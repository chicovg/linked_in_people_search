package com.captech.appdevsearch.client;

import com.captech.appdevsearch.model.LinkedInSearchResult;
import com.captech.appdevsearch.model.People;
import com.captech.appdevsearch.model.Profile;
import com.captech.appdevsearch.model.SearchStatus;
import com.captech.appdevsearch.repository.ProfileRepository;
import com.captech.appdevsearch.repository.SearchStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.captech.appdevsearch.Constants.PROFILE_Q;

/**
 * Created by victorguthrie on 3/6/15.
 */
@Component
public class LinkedInClient {

    private static final Logger logger = LoggerFactory.getLogger(LinkedInClient.class);

    @Autowired private SearchStatusRepository searchStatusRepository;
    @Autowired private ProfileRepository profileRepository;
    @Autowired private JmsTemplate jmsTemplate;

    @Value("${linkedInSearchUrl}") String searchUrl;
    @Value("${linkedInRequestLimit}") int requestLimit;
    @Value("${linkedInPageSize}") int pageSize;

    private RestTemplate restTemplate;

    public void fetchLinkedInProfiles(String token, String keywords){
        logger.info("Starting Linked In Search ...");
        SearchStatus searchStatus = searchStatusRepository.findFirstByOrderByIdAsc();
        if(null==searchStatus){
            searchStatus = new SearchStatus(pageSize);
        }

        int requests = 0;
        int count = 0;
        int total = 0;
        do {
            Map<String, String> vars = new HashMap<>();
            vars.put("format", "json");
            vars.put("token", token);
            vars.put("start", String.valueOf(count));
            vars.put("count", String.valueOf(pageSize));
            vars.put("keywords", keywords);

            try{
                ResponseEntity<LinkedInSearchResult> result = getRestTemplate().getForEntity(searchUrl, LinkedInSearchResult.class, vars);
                //ResponseEntity<String> result = getRestTemplate().getForEntity(searchUrl, String.class, vars);
                //logger.info(result.getBody());
                if(HttpStatus.OK.equals(result.getStatusCode()) && null != result.getBody()){
                    LinkedInSearchResult searchResult = result.getBody();
                    People people = searchResult.getPeople();
                    total = people.get_total();
                    if(null!=people && null!=people.getValues()){
                        for(Profile profile : searchResult.getPeople().getValues()){
                            logger.info(String.format("Got profile: %s %s", profile.getFirstName(), profile.getLastName()));
                            if(!"private".equalsIgnoreCase(profile.getLastName())){
                                profileRepository.save(profile);
                                jmsTemplate.convertAndSend(PROFILE_Q, profile);
                            }
                        }
                        count += people.get_count();
                    }
                } else {
                    logger.error("Error occurred during linked In search, exiting ...");
                    break;
                }
            } catch (Exception e){
                logger.error("Exceptions occurred during linked In search, exiting ...", e);
                break;
            }

            searchStatus.setCount(count);
            searchStatus.setPageSize(pageSize);
            searchStatus.setTotal(total);
            searchStatusRepository.save(searchStatus);
        } while (requests < requestLimit && count < total);
    }

    private RestTemplate getRestTemplate(){
        if(null==restTemplate){
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }
}


