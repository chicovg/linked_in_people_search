package com.captech.appdevsearch.repository;

import com.captech.appdevsearch.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by victorguthrie on 3/6/15.
 */
public interface ProfileRepository extends MongoRepository<Profile, String> {

}
