package org.artem.servicemanagement.dto.nova.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetWarehousesRequestDto {

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("CityRef")
    private String cityRef;

    @JsonProperty("CategoryOfWarehouse")
    private String categoryOfWarehouse;

    @JsonProperty("FindByString")
    private String findByString;
}
