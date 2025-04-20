package org.quick.receipt.novapost.client;

import org.quick.receipt.novapost.dto.DeleteCounterpartyRequest;
import org.quick.receipt.novapost.entity.request.*;
import org.quick.receipt.novapost.entity.response.ContactPersonAddress;
import org.quick.receipt.novapost.entity.response.CounterpartyContact;
import org.quick.receipt.novapost.entity.response.CounterpartySender;
import org.quick.receipt.novapost.entity.response.ResponseBase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange(url = "/v2.0/json/", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface CounterpartyClient {

    @GetExchange
    ResponseBase<CounterpartySender> saveCounterparty(@RequestBody RequestBase<SaveCounterpartyRequest> request);

    @GetExchange
    ResponseBase<CounterpartySender> getCounterparties(@RequestBody RequestBase<GetCounterpartiesRequest> request);

    @GetExchange
    ResponseBase<CounterpartySender> deleteCounterparty(@RequestBody RequestBase<DeleteCounterpartyRequest> request);

    @GetExchange
    ResponseBase<CounterpartyContact> getCounterpartyContactPersons(@RequestBody RequestBase<GetCounterpartyContactPersonsRequest> request);

//    @GetExchange
//    ResponseBase<CounterpartyContact> saveCounterpartyContactPerson(@RequestBody RequestBase<SaveCounterpartyRequest> request);

    @GetExchange
    ResponseBase<ContactPersonAddress> saveAddressContactPerson(@RequestBody RequestBase<SaveAddressContactPersonRequest> request);

}