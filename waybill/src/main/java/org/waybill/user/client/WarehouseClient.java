package org.waybill.user.client;

import org.waybill.user.dto.nova.post.WarehouseReadDto;
import org.waybill.user.dto.nova.post.WarehouseRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "nova-post", url = "localhost:8090")
@Primary
public interface WarehouseClient {

    @GetMapping(value = "/api/v1/warehouses", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<WarehouseReadDto> findAll(@RequestBody WarehouseRequestDto warehousesRequest);
}