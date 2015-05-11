package com.captech.appdevsearch.model;

import java.util.List;

/**
 * Created by victorguthrie on 2/21/15.
 */
public class People {
    private int _count;
    private int _start;
    private int _total;
    private List<Profile> values;

    public int get_count() {
        return _count;
    }

    public void set_count(int _count) {
        this._count = _count;
    }

    public int get_start() {
        return _start;
    }

    public void set_start(int _start) {
        this._start = _start;
    }

    public int get_total() {
        return _total;
    }

    public void set_total(int _total) {
        this._total = _total;
    }

    public List<Profile> getValues() {
        return values;
    }

    public void setValues(List<Profile> values) {
        this.values = values;
    }
}
