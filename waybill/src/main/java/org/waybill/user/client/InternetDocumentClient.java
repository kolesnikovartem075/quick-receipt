package org.waybill.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.waybill.user.dto.nova.post.InternetDocumentCreateDto;
import org.waybill.user.dto.nova.post.InternetDocumentReadDto;

@FeignClient(name = "nova-post", url = "localhost:8090")
@Primary
public interface InternetDocumentClient {

    @PostMapping(value = "/api/v1/internet-document", consumes = MediaType.APPLICATION_JSON_VALUE)
    InternetDocumentReadDto create(@RequestBody InternetDocumentCreateDto documentCreateDto);
}