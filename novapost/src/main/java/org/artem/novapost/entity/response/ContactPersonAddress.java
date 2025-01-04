package org.artem.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContactPersonAddress {

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("ContactPersonRef")
    private String contactPersonRef;

    @JsonProperty("CityRef")
    private String cityRef;

    @JsonProperty("CityDescription")
    private String cityDescription;

    @JsonProperty("AddressDescription")
    private String addressDescription;

    @JsonProperty("General")
    private Integer general;
}
