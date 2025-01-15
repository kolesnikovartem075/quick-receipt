package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchSettlementsRequest {


    @JsonProperty("CityName")
    private String cityName;

    @JsonProperty("Limit")
    private Integer limit;

    @JsonProperty("Page")
    private Integer page;
}
