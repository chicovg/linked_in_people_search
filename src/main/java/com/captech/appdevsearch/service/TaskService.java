package com.captech.appdevsearch.service;

import com.captech.appdevsearch.client.AppleStoreClient;
import com.captech.appdevsearch.client.GoogleStoreClient;
import com.captech.appdevsearch.client.LinkedInClient;
import com.captech.appdevsearch.model.Profile;
import com.captech.appdevsearch.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import static com.captech.appdevsearch.Constants.PROFILE_Q;

/**
 * Created by victorguthrie on 3/6/15.
 */
@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired private ProfileRepository profileRepository;
    @Autowired private LinkedInClient linkedInClient;
    @Autowired private AppleStoreClient appleStoreClient;
    @Autowired private GoogleStoreClient googleStoreClient;

    @Scheduled(cron = "${linkedInSearchSchedule}")
    public void launchScheduledLinkedInProfileFetch(){
    }

    @Async
    public void launchLinkedInProfileFetchOnDemand(String token, String keywords){
        //linkedInClient.fetchLinkedInProfiles(token, keywords);
        processProfilesFromDb();
    }

    private void processProfilesFromDb(){
        Iterable<Profile> profiles = profileRepository.findAll();
        Iterator<Profile> iterator = profiles.iterator();
        while (iterator.hasNext()){
            Profile profile = iterator.next();
            logger.info(String.format("Searching profile: %s %s", profile.getFirstName(), profile.getLastName()));
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            profile = appleStoreClient.searchAppsByDeveloper(profile);
            profile = googleStoreClient.searchAppsByDeveloper(profile);
            profileRepository.save(profile);
        }
    }

    @JmsListener(destination = PROFILE_Q, containerFactory = "jmsListenerContainerFactory")
    public void handleProfileMessage(Profile profile) {
        logger.info(String.format("Got profile: %s %s", profile.getFirstName(), profile.getLastName()));
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        profile = appleStoreClient.searchAppsByDeveloper(profile);
        profile = googleStoreClient.searchAppsByDeveloper(profile);
        profileRepository.save(profile);
    }

    public Profile searchGooglePlay(String searchString){
        Profile p = new Profile();
        p.setFirstName(searchString);
        p.setLastName("");
        return googleStoreClient.searchAppsByDeveloper(p);
    }

    public Profile searchAppleStore(String searchString){
        Profile p = new Profile();
        p.setFirstName(searchString);
        p.setLastName("");
        return appleStoreClient.searchAppsByDeveloper(p);
    }

}
