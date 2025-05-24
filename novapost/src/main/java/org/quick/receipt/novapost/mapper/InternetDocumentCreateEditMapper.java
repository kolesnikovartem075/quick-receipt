package org.quick.receipt.novapost.mapper;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.InternetDocumentCreateDto;
import org.quick.receipt.novapost.entity.request.GetCounterpartyContactPersonsRequest;
import org.quick.receipt.novapost.entity.request.SaveInternetDocumentRequest;
import org.quick.receipt.novapost.service.CounterpartyContactService;
import org.quick.receipt.novapost.service.CounterpartyService;
import org.quick.receipt.novapost.util.DateUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InternetDocumentCreateEditMapper implements Mapper<InternetDocumentCreateDto, SaveInternetDocumentRequest> {


    private final CounterpartyContactService counterpartyContactService;
    private final CounterpartyService counterpartyService;

    @Override
    public SaveInternetDocumentRequest map(InternetDocumentCreateDto object) {
        var sender = object.getAccountContact();
        var senderRef = counterpartyService.getSenderRef(sender.getApiKey());
        var senderCity = sender.getContact().getWarehouse().getCity().getRef();

        var recipientContact = object.getUser().getContact();
        var recipientCity = recipientContact.getWarehouse().getCity().getRef();
        var findCounterpartyRequest = new GetCounterpartyContactPersonsRequest(senderRef, recipientContact.getPhoneNumber(), "1");
        var recipient = counterpartyContactService.getOrCreate(sender.getApiKey(), findCounterpartyRequest, recipientContact);

        var paymentProperties = object.getPaymentProperties();

        return SaveInternetDocumentRequest.builder()
                // Sender-related fields
                .citySender(senderCity)
                .sender(senderRef)
                .senderAddress(object.getAccountContact().getContact().getWarehouse().getRef())
                .contactSender(senderRef)
                .sendersPhone(recipientContact.getPhoneNumber())
                // Recipient-related fields
                .cityRecipient(recipientCity)
                .recipient(senderRef)
                .recipientAddress(recipientContact.getWarehouse().getRef())
                .contactRecipient(recipient.getRef())
                .recipientsPhone(recipientContact.getPhoneNumber())
                // Payment and cargo details
                .payerType(paymentProperties.getPayerType())
                .paymentMethod(paymentProperties.getPaymentMethod())
                .cargoType(paymentProperties.getCargoType())
                .weight(paymentProperties.getWeight())
                .seatsAmount(paymentProperties.getSeatsAmount())
                .description(object.getOrder().getDescription())
                .cost(paymentProperties.getCost())
                // Date formatting
                .dateTime(DateUtils.from(object.getCreatedAt()))
                .build();
    }
}