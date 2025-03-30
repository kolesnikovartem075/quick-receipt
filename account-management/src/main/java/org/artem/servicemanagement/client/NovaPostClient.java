package org.artem.servicemanagement.client;

import org.artem.servicemanagement.dto.nova.post.City;
import org.artem.servicemanagement.dto.nova.post.GetWarehousesRequestDto;
import org.artem.servicemanagement.dto.nova.post.Warehouse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "nova-post", url = "localhost:8080")
@Primary
public interface NovaPostClient {

    @PostMapping(value = "/api/nova-post/warehouses", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Warehouse> getWarehouses(@RequestBody GetWarehousesRequestDto request);

    @PostMapping(value = "/api/nova-post/cities", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<City> getCities(@RequestBody GetWarehousesRequestDto request);
}