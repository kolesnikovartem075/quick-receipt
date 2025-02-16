package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.dto.nova.post.Warehouse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseReadMapper implements Mapper<Warehouse, PostOfficeReadDto> {

    @Override
    public PostOfficeReadDto map(Warehouse object) {
        return new PostOfficeReadDto(object.getRef(), object.getDescription(), object.getShortAddress(), object.getCityDescription());
    }
}