package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.client.NovaPostClient;
import org.artem.servicemanagement.dto.nova.post.GetWarehousesRequestDto;
import org.artem.servicemanagement.dto.nova.post.PostOfficeRequestDto;
import org.artem.servicemanagement.dto.nova.post.PostOfficeReadDto;
import org.artem.servicemanagement.mapper.WarehouseCreateEditMapper;
import org.artem.servicemanagement.mapper.WarehouseReadMapper;
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

    public Optional<PostOfficeReadDto> find(PostOfficeRequestDto request) {
        var map = getGetWarehousesRequestDto(request);
        return novaPostClient.getWarehouses(map).stream()
                .map(warehouseReadMapper::map)
                .findFirst();
    }

    private GetWarehousesRequestDto getGetWarehousesRequestDto(PostOfficeRequestDto request) {
        return warehouseCreateEditMapper.map(request);
    }
}