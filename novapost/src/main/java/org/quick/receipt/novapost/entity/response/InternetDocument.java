package org.quick.receipt.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InternetDocument {

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("CostOnSite")
    private String costOnSite;

    @JsonProperty("EstimatedDeliveryDate")
    private String estimatedDeliveryDate;

    @JsonProperty("IntDocNumber")
    private String intDocNumber;

    @JsonProperty("TypeDocument")
    private String typeDocument;
}