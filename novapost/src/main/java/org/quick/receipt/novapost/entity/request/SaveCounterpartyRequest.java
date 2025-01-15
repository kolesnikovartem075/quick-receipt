package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaveCounterpartyRequest {

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("MiddleName")
    private String middleName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("CounterpartyType")
    private String counterpartyType;

    @JsonProperty("CounterpartyProperty")
    private String counterpartyProperty;
}
