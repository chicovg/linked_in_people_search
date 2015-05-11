package com.captech.appdevsearch.client;

import com.captech.appdevsearch.model.AppleStoreAppDetail;
import com.captech.appdevsearch.model.AppleStoreSearchResult;
import com.captech.appdevsearch.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by victorguthrie on 3/6/15.
 */
@Component
public class AppleStoreClient {

    private static final Logger logger = LoggerFactory.getLogger(AppleStoreClient.class);

    @Value("${appleStoreSearchUrl}") String appleStoreSearchUrl;

    private RestTemplate restTemplate;

    public Profile searchAppsByDeveloper(Profile profile){
        try{
            Map<String, String> vars = new HashMap<>();
            String developerName = String.format("%s %s", profile.getFirstName(), profile.getLastName());
            vars.put("term", developerName);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AppleStoreSearchResult> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<AppleStoreSearchResult> result = getRestTemplate().exchange(appleStoreSearchUrl, HttpMethod.GET, httpEntity, AppleStoreSearchResult.class, vars);
            if(HttpStatus.OK.equals(result.getStatusCode()) && null != result.getBody()){
                AppleStoreSearchResult searchResult = result.getBody();
                logger.info("iOS apps: " + searchResult.getResults().size());
                List<AppleStoreAppDetail> iosApps = new LinkedList<>();
                for(AppleStoreAppDetail ad : searchResult.getResults()){
                    if(ad.getArtistName().equalsIgnoreCase(developerName)){
                        iosApps.add(ad);
                    }
                }
                profile.setIosApps(iosApps);
            }
        } catch (Exception e) {
            logger.error("Exception thrown while searching the Apple Store", e);
        }

        return profile;
    }

    private RestTemplate getRestTemplate(){
        if(null==restTemplate){
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            restTemplate = new RestTemplate(factory);
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(MediaType.parseMediaTypes("text/javascript"));
            restTemplate.getMessageConverters().add(converter);
        }
        return restTemplate;
    }

}
