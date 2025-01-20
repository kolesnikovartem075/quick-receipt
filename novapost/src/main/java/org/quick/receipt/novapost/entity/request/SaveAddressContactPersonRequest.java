package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaveAddressContactPersonRequest {

    @JsonProperty("ContactPersonRef")
    private String contactPersonRef;

    @JsonProperty("SettlementRef")
    private String settlementRef;

    @JsonProperty("AddressRef")
    private String addressRef;

    @JsonProperty("AddressType")
    private String addressType;
}
