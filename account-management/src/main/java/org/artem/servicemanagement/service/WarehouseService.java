package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.client.WarehouseClient;
import org.artem.servicemanagement.dto.nova.post.WarehouseReadDto;
import org.artem.servicemanagement.dto.nova.post.WarehouseRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseClient warehouseClient;

    public Optional<WarehouseReadDto> findBy(WarehouseRequestDto request) {
        if (request.getCategoryOfWarehouse() == null) {
            request.setCategoryOfWarehouse("Warehouse");
        }

        return warehouseClient.findAll(request).stream()
                .findFirst();
    }
}