package org.waybill.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.waybill.user.dto.AccountContactReadDto;

@FeignClient(name = "account", url = "localhost:8080/api/v1/accounts/{accountId}/contacts")
@Primary
public interface AccountContactClient {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AccountContactReadDto findById(@PathVariable Long id, @PathVariable Long accountId);
}