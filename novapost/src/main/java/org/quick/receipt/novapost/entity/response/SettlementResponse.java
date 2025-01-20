package org.quick.receipt.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SettlementResponse {

    @JsonProperty("TotalCount")
    private int totalCount;

    @JsonProperty("Addresses")
    private List<Settlement> settlements;
}