package com.captech.appdevsearch.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by victorguthrie on 2/25/15.
 */
@XmlRootElement
public class AppleStoreSearchResult implements Serializable{

    private int resultCount;

    private List<AppleStoreAppDetail> results;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<AppleStoreAppDetail> getResults() {
        return results;
    }

    public void setResults(List<AppleStoreAppDetail> results) {
        this.results = results;
    }
}
