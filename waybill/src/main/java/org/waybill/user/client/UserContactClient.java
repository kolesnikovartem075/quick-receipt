package org.waybill.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.waybill.user.dto.UserContactReadDto;

@FeignClient(name = "user-contacts", url = "localhost:8080/api/v1/accounts/{accountId}/users/{userId}/contacts")
@Primary
public interface UserContactClient {


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserContactReadDto findById(@PathVariable Long id,
                                @PathVariable Long accountId,
                                @PathVariable String userId);
}