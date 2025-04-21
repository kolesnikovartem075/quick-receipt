package org.waybill.account.management.service;

import lombok.RequiredArgsConstructor;
//import org.waybill.account.management.client.WaybillClient;
import org.waybill.account.management.dto.nova.post.WaybillCreateEditDto;
import org.waybill.account.management.dto.nova.post.WaybillReadDto;
import org.waybill.account.management.mapper.WaybillCreateEditMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaybillService {

//    private final WaybillClient waybillClient;
    private final WaybillCreateEditMapper waybillCreateEditMapper;

//    public WaybillReadDto create(WaybillCreateEditDto waybillDto) {
//        return Optional.of(waybillDto)
//                .map(waybillCreateEditMapper::map)
//                .map(waybillClient::create)
//                .orElseThrow();
//    }

}