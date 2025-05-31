package org.waybill.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.waybill.user.dto.OrderReadDto;

@FeignClient(name = "orders", url = "localhost:8080")
@Primary
public interface OrderClient {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderReadDto findById(@PathVariable Long id);
}