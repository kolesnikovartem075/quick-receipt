package org.waybill.user.client;

import org.waybill.user.dto.nova.post.CityReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "nova-post", url = "localhost:8090")
@Primary
public interface CityClient {

    @GetMapping(value = "/{queryString}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CityReadDto> find(@PathVariable String queryString);
}