package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetWarehousesRequest {

    @JsonProperty("CityRef")
    private String cityRef;

    @JsonProperty("CategoryOfWarehouse")
    private String categoryOfWarehouse;

    @JsonProperty("FindByString")
    private String findByString;
}
