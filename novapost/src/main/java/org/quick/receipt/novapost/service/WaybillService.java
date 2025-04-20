package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.CounterpartyType;
import org.quick.receipt.novapost.dto.WaybillReadDto;
import org.quick.receipt.novapost.dto.WaybillRequestDto;
import org.quick.receipt.novapost.entity.request.GetCounterpartiesRequest;
import org.quick.receipt.novapost.entity.request.SaveInternetDocumentRequest;
import org.quick.receipt.novapost.entity.response.CounterpartySender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class WaybillService {

    private final NovaPostService novaPostService;


    public WaybillReadDto create(WaybillRequestDto waybillRequest) {
        var senderCounterparty = getCounterparty(CounterpartyType.SENDER);
        var sender = waybillRequest.getAccountSender();
        var senderCity = sender.getWarehouseReadDto().getCity().getRef();
//        novaPostService.getCounterpartyContactPersons()

        var recipientCounterparty = getCounterparty(CounterpartyType.RECIPIENT);
        var user = waybillRequest.getUser();
        var userCity = user.getWarehouseReadDto().getCity().getRef();

        var internetDocumentRequest = new SaveInternetDocumentRequest();
        internetDocumentRequest.setPayerType("Sender");
        internetDocumentRequest.setPaymentMethod("Cash");
        internetDocumentRequest.setDateTime(waybillRequest.getCreatedAt().format(DateTimeFormatter.ofPattern("d.MM.yyyy")));
        internetDocumentRequest.setCargoType("Parcel");
        internetDocumentRequest.setWeight("0,1");
        internetDocumentRequest.setSeatsAmount(1);
        internetDocumentRequest.setDescription(waybillRequest.getOrder().getDescription());
        internetDocumentRequest.setCost(0f);
        internetDocumentRequest.setCitySender(senderCity);
        internetDocumentRequest.setSender(senderCounterparty);
        internetDocumentRequest.setSenderAddress(sender.getWarehouseReadDto().getRef());
        internetDocumentRequest.setContactSender(null);
        internetDocumentRequest.setSendersPhone(user.getPhoneNumber());
        internetDocumentRequest.setCityRecipient(userCity);
        internetDocumentRequest.setRecipient(recipientCounterparty);
        internetDocumentRequest.setRecipientAddress(user.getWarehouseReadDto().getRef());
        internetDocumentRequest.setContactRecipient(null);
        internetDocumentRequest.setRecipientsPhone(user.getPhoneNumber());


        var internetDocuments = novaPostService.saveInternetDocument(internetDocumentRequest);
        return null;
    }

    private String getCounterparty(CounterpartyType counterpartyType) {
        return novaPostService.getCounterparties(GetCounterpartiesRequest.of(counterpartyType))
                .stream().findFirst()
                .map(CounterpartySender::getCounterparty)
                .orElseThrow();
    }
}