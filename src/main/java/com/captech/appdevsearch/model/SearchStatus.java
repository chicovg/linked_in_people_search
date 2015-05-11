package com.captech.appdevsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by victorguthrie on 2/21/15.
 */
@Document(collection = "search_status")
public class SearchStatus {

    @Id
    private String id;
    private int count;
    private int pageSize;
    private int total;

    public SearchStatus() {
    }

    public SearchStatus(int pageSize) {
        this.pageSize = pageSize;
        this.count = 0;
        this.total = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
