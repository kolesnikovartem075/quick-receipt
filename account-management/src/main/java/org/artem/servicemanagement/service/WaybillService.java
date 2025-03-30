package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.dto.AdminReadDto;
import org.artem.servicemanagement.dto.WaybillCreateEditDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaybillService {

    private final WaybillCreateEditMapper waybillCreateEditMapper;
    private final AccountSenderService accountSenderService;

    public AdminReadDto create(WaybillCreateEditDto waybillDto) {
        var accountSender = getAccountSender(waybillDto);
        var orderId = waybillDto.getOrderId();


        return Optional.of(adminDto)
                .map(waybillCreateEditMapper::map)
                .map(adminReadMapper::map)
                .orElseThrow();
    }

    private AccountSenderReadDto getAccountSender(WaybillCreateEditDto waybillDto) {
        return accountSenderService.findById(waybillDto.getAccountSenderId()).orElseThrow();
    }
}