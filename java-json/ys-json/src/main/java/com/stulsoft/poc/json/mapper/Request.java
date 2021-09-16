/*
 * Copyright (c) 2021. Yuriy Stul
 */

package com.stulsoft.poc.json.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

/**
 * @author Yuriy Stul
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    private List<Filter> filters;

    public Request(){}

    private void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public Optional<List<Filter>> filterList(){
        return Optional.ofNullable(filters);
    }

    @Override
    public String toString() {
        return "Request{" +
                "filters=" + filters +
                '}';
    }
}
