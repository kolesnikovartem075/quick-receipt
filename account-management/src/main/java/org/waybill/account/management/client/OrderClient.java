package org.waybill.account.management.client;

import org.waybill.account.management.dto.OrderReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order", url = "localhost:8080")
@Primary
public interface OrderClient {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderReadDto findById(@PathVariable Long id);
}