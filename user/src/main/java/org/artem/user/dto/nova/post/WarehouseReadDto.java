package org.artem.user.dto.nova.post;

import lombok.Value;

@Value
public class WarehouseReadDto {

    String ref;
    String description;
    String shortAddress;
    String cityDescription;
}
