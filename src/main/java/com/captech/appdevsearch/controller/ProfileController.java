package com.captech.appdevsearch.controller;

import com.captech.appdevsearch.model.Profile;
import com.captech.appdevsearch.service.ProfileService;
import com.captech.appdevsearch.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by victorguthrie on 3/6/15.
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired private TaskService taskService;
    @Autowired private ProfileService profileService;

    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public ResponseEntity<String> triggerLinkedInFetch(@RequestParam("token") String token,
                                                       @RequestParam("keywords") String keywords){
        taskService.launchLinkedInProfileFetchOnDemand(token, keywords);
        return new ResponseEntity<>("Fetch Triggered", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Profile>> findProfiles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                      @RequestParam(value = "sort", defaultValue="default") String sort) {
        return new ResponseEntity<>(profileService.findProfiles(page, pageSize, sort), HttpStatus.OK);
    }

    @RequestMapping(value = "google-play-search", method = RequestMethod.GET)
    public ResponseEntity<Profile> searchGoogle(@RequestParam("search") String searchString){
        return new ResponseEntity<>(taskService.searchGooglePlay(searchString), HttpStatus.OK);
    }

    @RequestMapping(value = "apple-store-search", method = RequestMethod.GET)
    public ResponseEntity<Profile> searchApple(@RequestParam("search") String searchString){
        return new ResponseEntity<>(taskService.searchAppleStore(searchString), HttpStatus.OK);
    }
}
