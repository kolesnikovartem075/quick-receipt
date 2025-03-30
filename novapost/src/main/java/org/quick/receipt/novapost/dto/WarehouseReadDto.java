package org.quick.receipt.novapost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseReadDto {

    String ref;
    String description;
    String shortAddress;
    String cityDescription;
}
