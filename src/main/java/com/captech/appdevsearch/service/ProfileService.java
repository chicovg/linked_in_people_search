package com.captech.appdevsearch.service;

import com.captech.appdevsearch.model.Profile;
import com.captech.appdevsearch.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by victorguthrie on 3/10/15.
 */
@Service
public class ProfileService {

    @Autowired private ProfileRepository profileRepository;

    public Page<Profile> findProfiles(int page, int size, String sort){
        PageRequest pageRequest = new PageRequest(page, size, getSort(sort));
        return profileRepository.findAll(pageRequest);
    }

    private Sort getSort(String sort){
        if("ios".equalsIgnoreCase(sort)){
            return new Sort(new Sort.Order(Sort.Direction.DESC, "iosAppCount"));
        } else if("droid".equalsIgnoreCase(sort)){
            return new Sort(new Sort.Order(Sort.Direction.DESC, "androidAppCount"));
        } else if("fname".equalsIgnoreCase(sort)){
            return new Sort(new Sort.Order(Sort.Direction.ASC, "firstName"));
        } else if("lname".equalsIgnoreCase(sort)){
            return new Sort(new Sort.Order(Sort.Direction.ASC, "lastName"));
        } else {
            return new Sort(new Sort.Order(Sort.Direction.DESC, "iosAppCount"), new Sort.Order(Sort.Direction.DESC, "androidAppCount"));
        }
    }

}
