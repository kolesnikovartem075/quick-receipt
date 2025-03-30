package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.nova.post.PostOfficeReadDto;
import org.artem.servicemanagement.dto.nova.post.Warehouse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseReadMapper implements Mapper<Warehouse, PostOfficeReadDto> {

    @Override
    public PostOfficeReadDto map(Warehouse object) {
        return new PostOfficeReadDto(object.getRef(), object.getDescription(), object.getShortAddress(), object.getCityDescription());
    }
}