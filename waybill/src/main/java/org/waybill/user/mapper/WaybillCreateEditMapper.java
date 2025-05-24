package org.waybill.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.user.dto.AccountContactReadDto;
import org.waybill.user.dto.OrderReadDto;
import org.waybill.user.dto.UserContactReadDto;
import org.waybill.user.dto.WaybillCreateEditDto;
import org.waybill.user.dto.nova.post.InternetDocumentCreateDto;

@Component
@RequiredArgsConstructor
public class WaybillCreateEditMapper implements Mapper<WaybillCreateEditDto, InternetDocumentCreateDto> {

    @Override
    public InternetDocumentCreateDto map(WaybillCreateEditDto object) {
        InternetDocumentCreateDto admin = new InternetDocumentCreateDto();
        copy(object, admin);

        return admin;
    }

    private void copy(WaybillCreateEditDto object, InternetDocumentCreateDto waybill) {
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