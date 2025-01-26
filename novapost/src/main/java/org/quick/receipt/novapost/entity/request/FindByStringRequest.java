package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FindByStringRequest {

    @JsonProperty("FindByString")
    private String findByString;
}