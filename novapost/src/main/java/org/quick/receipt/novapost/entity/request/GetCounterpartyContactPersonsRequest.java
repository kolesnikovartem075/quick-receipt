package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetCounterpartyContactPersonsRequest {

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("FindByString")
    private String findByString;

    @JsonProperty("Page")
    private String page;
}
