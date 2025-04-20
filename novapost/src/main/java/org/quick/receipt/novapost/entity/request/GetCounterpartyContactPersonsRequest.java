package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCounterpartyContactPersonsRequest {

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("FindByString")
    private String findByString;

    @JsonProperty("Page")
    private String page;
}
