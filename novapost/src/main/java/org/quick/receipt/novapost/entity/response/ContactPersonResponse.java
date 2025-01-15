package org.quick.receipt.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContactPersonResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private List<ContactPerson> data;
}
