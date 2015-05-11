package com.captech.appdevsearch.model;

/**
 * Created by victorguthrie on 2/21/15.
 *
 *
 * {
 "numResults": 2535,
 "people": {
 "_count": 10,
 "_start": 0,
 "_total": 2535,
 "values": [
 */
public class LinkedInSearchResult {

    private int numResults;
    private People people;

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}
