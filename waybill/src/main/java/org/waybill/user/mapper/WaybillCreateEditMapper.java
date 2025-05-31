package org.waybill.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.user.client.AccountContactClient;
import org.waybill.user.client.OrderClient;
import org.waybill.user.client.UserContactClient;
import org.waybill.user.dto.AccountContactReadDto;
import org.waybill.user.dto.OrderReadDto;
import org.waybill.user.dto.UserContactReadDto;
import org.waybill.user.dto.WaybillCreateEditDto;
import org.waybill.user.dto.nova.post.InternetDocumentCreateDto;

@Component
@RequiredArgsConstructor
public class WaybillCreateEditMapper implements Mapper<WaybillCreateEditDto, InternetDocumentCreateDto> {

    private final OrderClient orderClient;
    private final AccountContactClient accountContactClient;
    private final UserContactClient userContactClient;

    @Override
    public InternetDocumentCreateDto map(WaybillCreateEditDto object) {
        InternetDocumentCreateDto admin = new InternetDocumentCreateDto();
        copy(object, admin);

        return admin;
    }

    private void copy(WaybillCreateEditDto object, InternetDocumentCreateDto waybill) {
        var order = getOrder(object);
        var user = getUser(order);
        var accountContact = getAccountContact(object);

        waybill.setUser(user);
        waybill.setOrder(order);
        waybill.setAccountContact(accountContact);
        waybill.setCreatedAt(object.getCreatedAt());
    }


    private UserContactReadDto getUser(OrderReadDto order) {
        return userContactClient.findById(order.getUserContactId(), null, null);
    }

    private OrderReadDto getOrder(WaybillCreateEditDto waybillDto) {
        return orderClient.findById(waybillDto.getOrderId());
    }

    private AccountContactReadDto getAccountContact(WaybillCreateEditDto waybillDto) {
        return accountContactClient.findById(waybillDto.getAccountSenderId(), null);
    }
}