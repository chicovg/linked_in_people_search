package com.captech.appdevsearch.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Represents a LinkedIn profile
 *
 * Created by victorguthrie on 2/21/15.
 */
@Document(collection = "profile")
public class Profile implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    private int iosAppCount;
    private int androidAppCount;

    private List<AppleStoreAppDetail> iosApps;
    private List<AndroidAppDetail> androidApps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AppleStoreAppDetail> getIosApps() {
        return iosApps;
    }

    public void setIosApps(List<AppleStoreAppDetail> iosApps) {
        this.iosApps = iosApps;
        this.iosAppCount = null != iosApps ? iosApps.size() : 0;
    }

    public List<AndroidAppDetail> getAndroidApps() {
        return androidApps;
    }

    public void setAndroidApps(List<AndroidAppDetail> androidApps) {
        this.androidApps = androidApps;
        this.androidAppCount = null != androidApps ? androidApps.size() : 0;
    }

    public int getIosAppCount() {
        return iosAppCount;
    }

    public void setIosAppCount(int iosAppCount) {
        this.iosAppCount = iosAppCount;
    }

    public int getAndroidAppCount() {
        return androidAppCount;
    }

    public void setAndroidAppCount(int androidAppCount) {
        this.androidAppCount = androidAppCount;
    }
}
