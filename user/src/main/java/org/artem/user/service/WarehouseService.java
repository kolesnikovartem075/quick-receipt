package org.artem.user.service;

import lombok.RequiredArgsConstructor;
import org.artem.user.client.NovaPostClient;
import org.artem.user.dto.nova.post.GetWarehousesRequestDto;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.mapper.WarehouseReadMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final NovaPostClient novaPostClient;
    private final WarehouseReadMapper warehouseReadMapper;

    public Optional<PostOfficeReadDto> findByRef(String warehouseRef) {
        GetWarehousesRequestDto request = new GetWarehousesRequestDto();
        request.setRef(warehouseRef);
        request.setCategoryOfWarehouse("Warehouse");

        return novaPostClient.getWarehouses(request).stream()
                .map(warehouseReadMapper::map)
                .findFirst();
    }

    public Optional<PostOfficeReadDto> findBy(String queryString, String cityRef) {
        GetWarehousesRequestDto request = new GetWarehousesRequestDto();
        request.setCategoryOfWarehouse("Warehouse");
        request.setFindByString(queryString);
        request.setCityRef(cityRef);

        return novaPostClient.getWarehouses(request).stream()
                .map(warehouseReadMapper::map)
                .findFirst();
    }
}