package org.artem.servicemanagement.dto.nova.post;

import lombok.Data;

@Data
public class PostOfficeRequestDto {

    private String ref;

    private String cityRef;

    private String categoryOfWarehouse;

    private String findByString;
}
