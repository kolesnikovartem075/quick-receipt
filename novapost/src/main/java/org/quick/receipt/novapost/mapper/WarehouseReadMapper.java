package org.quick.receipt.novapost.mapper;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.WarehouseReadDto;
import org.quick.receipt.novapost.entity.response.Warehouse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseReadMapper implements Mapper<Warehouse, WarehouseReadDto> {

    private final CityReadMapper cityReadMapper;

    @Override
    public WarehouseReadDto map(Warehouse object) {
        var city = cityReadMapper.map(object);

        return WarehouseReadDto.builder()
                .ref(object.getRef())
                .description(object.getDescription())
                .shortAddress(object.getShortAddress())
                .city(city)
                .build();
    }
}