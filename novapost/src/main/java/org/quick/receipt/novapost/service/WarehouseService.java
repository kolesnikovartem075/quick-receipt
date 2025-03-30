package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.WarehouseReadDto;
import org.quick.receipt.novapost.entity.request.GetWarehousesRequest;
import org.quick.receipt.novapost.mapper.WarehouseReadMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final NovaPostService novaPostService;
    private final WarehouseReadMapper warehouseReadMapper;

    public List<WarehouseReadDto> findAll(GetWarehousesRequest request) {
        if (request.getCategoryOfWarehouse() == null) {
            request.setCategoryOfWarehouse("Warehouse");
        }

        return novaPostService.getWarehouses(request).stream()
                .map(warehouseReadMapper::map)
                .toList();
    }
}