package org.quick.receipt.novapost.controller;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.WarehouseReadDto;
import org.quick.receipt.novapost.entity.request.GetWarehousesRequest;
import org.quick.receipt.novapost.service.WarehouseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {


    private final WarehouseService warehouseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WarehouseReadDto> findAll(GetWarehousesRequest warehousesRequest) {
        return warehouseService.findAll(warehousesRequest);
    }
}