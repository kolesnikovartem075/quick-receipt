package org.artem.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {

    @JsonProperty("Present")
    private String present;

    @JsonProperty("Warehouses")
    private int warehouses;

    @JsonProperty("MainDescription")
    private String mainDescription;

    @JsonProperty("Area")
    private String area;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("SettlementTypeCode")
    private String settlementTypeCode;

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("DeliveryCity")
    private String deliveryCity;

    @JsonProperty("AddressDeliveryAllowed")
    private boolean addressDeliveryAllowed;

    @JsonProperty("StreetsAvailability")
    private boolean streetsAvailability;

    @JsonProperty("ParentRegionTypes")
    private String parentRegionTypes;

    @JsonProperty("ParentRegionCode")
    private String parentRegionCode;

    @JsonProperty("RegionTypes")
    private String regionTypes;

    @JsonProperty("RegionTypesCode")
    private String regionTypesCode;
}
