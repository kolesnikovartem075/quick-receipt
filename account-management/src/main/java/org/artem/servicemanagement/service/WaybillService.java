package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.client.WaybillClient;
import org.artem.servicemanagement.dto.nova.post.WaybillCreateEditDto;
import org.artem.servicemanagement.dto.nova.post.WaybillReadDto;
import org.artem.servicemanagement.mapper.WaybillCreateEditMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaybillService {

    private final WaybillClient waybillClient;
    private final WaybillCreateEditMapper waybillCreateEditMapper;

    public WaybillReadDto create(WaybillCreateEditDto waybillDto) {
        return Optional.of(waybillDto)
                .map(waybillCreateEditMapper::map)
                .map(waybillClient::create)
                .orElseThrow();
    }

}