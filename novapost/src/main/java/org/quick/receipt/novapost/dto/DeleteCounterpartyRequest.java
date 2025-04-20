package org.quick.receipt.novapost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteCounterpartyRequest {

    @JsonProperty("Ref")
    private String ref;
}
