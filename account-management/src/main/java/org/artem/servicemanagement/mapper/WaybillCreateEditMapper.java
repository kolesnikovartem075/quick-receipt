package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.client.OrderClient;
import org.artem.servicemanagement.client.UserProfileClient;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.dto.OrderReadDto;
import org.artem.servicemanagement.dto.UserProfileReadDto;
import org.artem.servicemanagement.dto.nova.post.WaybillCreateEditDto;
import org.artem.servicemanagement.dto.nova.post.WaybillRequestDto;
import org.artem.servicemanagement.service.AccountContactService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaybillCreateEditMapper implements Mapper<WaybillCreateEditDto, WaybillRequestDto> {


    private final AccountContactService accountSenderService;
    private final OrderClient orderClient;
    private final UserProfileClient userProfileClient;

    @Override
    public WaybillRequestDto map(WaybillCreateEditDto fromObject, WaybillRequestDto toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public WaybillRequestDto map(WaybillCreateEditDto object) {
        WaybillRequestDto admin = new WaybillRequestDto();
        copy(object, admin);

        return admin;
    }

    private void copy(WaybillCreateEditDto object, WaybillRequestDto waybill) {
        var order = getOrder(object);
        var user = getUser(order);
        var accountSender = getAccountSender(object);

        waybill.setAccountSender(accountSender);
        waybill.setUser(user);
        waybill.setOrder(order);
        waybill.setCreatedAt(object.getCreatedAt());
    }


    private UserProfileReadDto getUser(OrderReadDto order) {
        return userProfileClient.findById(order.getUserId());
    }

    private OrderReadDto getOrder(WaybillCreateEditDto waybillDto) {
        return orderClient.findById(waybillDto.getOrderId());
    }

    private AccountSenderReadDto getAccountSender(WaybillCreateEditDto waybillDto) {
        return accountSenderService.findById(waybillDto.getAccountSenderId()).orElseThrow();
    }
}