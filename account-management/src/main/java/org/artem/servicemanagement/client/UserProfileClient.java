package org.artem.servicemanagement.client;

import org.artem.servicemanagement.dto.UserProfileReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "localhost:8080")
@Primary
public interface UserProfileClient {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileReadDto findById(@PathVariable Long id);
}