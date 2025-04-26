package org.waybill.account.management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.waybill.account.management.dto.nova.post.CityReadDto;

import java.util.List;

@FeignClient(name = "nova-post-city", url = "localhost:8090", path = "/api/v1/cities")
public interface CityClient {

    @GetMapping(value = "/{queryString}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CityReadDto> find(@PathVariable String queryString);
}