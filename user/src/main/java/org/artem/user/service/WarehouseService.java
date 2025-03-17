package org.artem.user.service;

import lombok.RequiredArgsConstructor;
import org.artem.user.client.NovaPostClient;
import org.artem.user.dto.nova.post.PostOfficeDto;
import org.artem.user.dto.nova.post.GetWarehousesRequestDto;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.mapper.WarehouseCreateEditMapper;
import org.artem.user.mapper.WarehouseReadMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final NovaPostClient novaPostClient;
    private final WarehouseReadMapper warehouseReadMapper;
    private final WarehouseCreateEditMapper warehouseCreateEditMapper;

    public Optional<PostOfficeReadDto> findByRef(String warehouseRef) {
        GetWarehousesRequestDto request = new GetWarehousesRequestDto();
        request.setRef(warehouseRef);
        request.setCategoryOfWarehouse("Warehouse");

        return novaPostClient.getWarehouses(request).stream()
                .map(warehouseReadMapper::map)
                .findFirst();
    }

    public Optional<PostOfficeReadDto> find(PostOfficeDto request) {
        var map = getGetWarehousesRequestDto(request);
        return novaPostClient.getWarehouses(map).stream()
                .map(warehouseReadMapper::map)
                .findFirst();
    }

    private GetWarehousesRequestDto getGetWarehousesRequestDto(PostOfficeDto request) {
        return warehouseCreateEditMapper.map(request);
    }
}