package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCounterpartiesRequest {

    @JsonProperty("CounterpartyProperty")
    private String counterpartyProperty;

    @JsonProperty("GetPrivatePerson")
    private String getPrivatePerson;
}
