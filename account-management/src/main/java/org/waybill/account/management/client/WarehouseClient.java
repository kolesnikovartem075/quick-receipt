package org.waybill.account.management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.waybill.account.management.dto.nova.post.WarehouseReadDto;
import org.waybill.account.management.dto.nova.post.WarehouseRequestDto;

import java.util.List;

@FeignClient(name = "nova-post-warehouse", url = "localhost:8090", path = "/api/v1/warehouses")
public interface WarehouseClient {

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    List<WarehouseReadDto> findAll(@SpringQueryMap WarehouseRequestDto warehousesRequest);
}