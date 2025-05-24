package org.waybill.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.waybill.user.client.InternetDocumentClient;
import org.waybill.user.dto.WaybillCreateEditDto;
import org.waybill.user.dto.nova.post.InternetDocumentReadDto;
import org.waybill.user.mapper.WaybillCreateEditMapper;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WaybillService {


    private final InternetDocumentClient internetDocumentClient;
    private final WaybillCreateEditMapper waybillCreateEditMapper;

    public InternetDocumentReadDto create(WaybillCreateEditDto waybillDto) {
        return Optional.of(waybillDto)
                .map(waybillCreateEditMapper::map)
                .map(internetDocumentClient::create)
                .orElseThrow();
    }

}