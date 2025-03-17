package org.artem.user.dto.nova.post;

import lombok.Data;

@Data
public class PostOfficeDto {

    private String ref;

    private String cityRef;

    private String categoryOfWarehouse;

    private String findByString;
}
