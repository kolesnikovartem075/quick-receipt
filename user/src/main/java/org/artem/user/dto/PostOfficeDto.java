package org.artem.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostOfficeDto {

    private String ref;

    private String cityRef;

    private String categoryOfWarehouse;

    private String findByString;
}
