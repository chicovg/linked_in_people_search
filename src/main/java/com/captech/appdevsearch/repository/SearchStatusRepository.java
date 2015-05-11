package com.captech.appdevsearch.repository;

import com.captech.appdevsearch.model.SearchStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by victorguthrie on 3/15/15.
 */
public interface SearchStatusRepository extends CrudRepository<SearchStatus, String> {
    public SearchStatus findFirstByOrderByIdAsc();
}
