package org.waybill.account.management.service;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.client.WarehouseClient;
import org.waybill.account.management.dto.nova.post.WarehouseReadDto;
import org.waybill.account.management.dto.nova.post.WarehouseRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseClient warehouseClient;

    public Optional<WarehouseReadDto> findByRef(String ref) {
        var warehouseRequestDto = new WarehouseRequestDto();
        warehouseRequestDto.setRef(ref);

        return findBy(warehouseRequestDto);
    }

    public Optional<WarehouseReadDto> findBy(WarehouseRequestDto request) {
        if (request.getCategoryOfWarehouse() == null) {
            request.setCategoryOfWarehouse("Warehouse");
        }

        return warehouseClient.findAll(request).stream()
                .findFirst();
    }
}