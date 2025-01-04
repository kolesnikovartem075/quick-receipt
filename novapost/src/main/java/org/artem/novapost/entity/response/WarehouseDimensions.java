package org.artem.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WarehouseDimensions {

    @JsonProperty("Width")
    private int width;

    @JsonProperty("Height")
    private int height;

    @JsonProperty("Length")
    private int length;
}
