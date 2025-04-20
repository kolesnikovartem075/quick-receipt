package org.quick.receipt.novapost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteCounterpartyResponse {

    @JsonProperty("Ref")
    private String ref;
}
