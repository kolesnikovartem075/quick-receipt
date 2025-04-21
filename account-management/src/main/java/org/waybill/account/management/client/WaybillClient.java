package org.waybill.account.management.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.waybill.account.management.dto.nova.post.WaybillReadDto;
import org.waybill.account.management.dto.nova.post.WaybillRequestDto;

//@FeignClient(name = "nova-post", url = "localhost:8090")
//@Primary
//public interface WaybillClient {
//
//    @GetMapping(value = "/api/v1/waybills", consumes = MediaType.APPLICATION_JSON_VALUE)
//    WaybillReadDto create(@RequestBody WaybillRequestDto waybillRequest);
//}