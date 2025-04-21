package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.account.management.client.OrderClient;
import org.waybill.account.management.dto.AccountContactReadDto;
import org.waybill.account.management.dto.OrderReadDto;
import org.waybill.account.management.dto.UserContactReadDto;
import org.waybill.account.management.dto.nova.post.WaybillCreateEditDto;
import org.waybill.account.management.dto.nova.post.WaybillRequestDto;
import org.waybill.account.management.service.AccountContactService;
import org.waybill.account.management.service.UserContactService;

@Component
@RequiredArgsConstructor
public class WaybillCreateEditMapper implements Mapper<WaybillCreateEditDto, WaybillRequestDto> {


    private final AccountContactService accountContactService;
    private final UserContactService userContactService;
    private final OrderClient orderClient;

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

        waybill.setUser(user);
        waybill.setOrder(order);
        waybill.setCreatedAt(object.getCreatedAt());
    }


    private UserContactReadDto getUser(OrderReadDto order) {
        return userContactService.findById(order.getUserContactId())
                .orElseThrow();
    }

    private OrderReadDto getOrder(WaybillCreateEditDto waybillDto) {
        return orderClient.findById(waybillDto.getOrderId());
    }

    private AccountContactReadDto getAccountSender(WaybillCreateEditDto waybillDto) {
        return accountContactService.findById(waybillDto.getAccountSenderId())
                .orElseThrow();
    }
}