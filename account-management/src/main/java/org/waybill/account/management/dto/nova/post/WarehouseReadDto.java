package org.waybill.account.management.dto.nova.post;

import lombok.Value;

@Value
public class WarehouseReadDto {

    String ref;
    String description;
    String shortAddress;
    CityReadDto city;
}