package org.waybill.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.waybill.user.dto.WaybillCreateEditDto;
import org.waybill.user.dto.nova.post.InternetDocumentReadDto;
import org.waybill.user.service.WaybillService;

@RestController
@RequestMapping("/api/v1/waybills")
@RequiredArgsConstructor
public class WaybillController {

    private final WaybillService waybillService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InternetDocumentReadDto create(@RequestBody WaybillCreateEditDto waybillCreateEditDto) {
        return waybillService.create(waybillCreateEditDto);
    }
}