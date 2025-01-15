package org.quick.receipt.novapost.client;

import org.quick.receipt.novapost.entity.request.*;
import org.quick.receipt.novapost.entity.response.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange(url = "/v2.0/json/", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface NovaPostClient {

    @GetExchange
    ResponseBase<City> getCities(@RequestBody RequestBase<FindByStringRequest> request);

    @GetExchange
    ResponseBase<CounterpartySender> getCounterparties(@RequestBody RequestBase<GetCounterpartiesRequest> request);

    @GetExchange
    ResponseBase<CargoDescription> getCargoDescriptions(@RequestBody RequestBase<FindByStringRequest> request);

    @GetExchange
    ResponseBase<SettlementResponse> searchSettlements(@RequestBody RequestBase<SearchSettlementsRequest> request);

    @GetExchange
    ResponseBase<Warehouse> getWarehouses(@RequestBody RequestBase<GetWarehousesRequest> request);

    @GetExchange
    ResponseBase<CounterpartyContact> getCounterpartyContactPersons(@RequestBody RequestBase<GetCounterpartyContactPersonsRequest> request);

    @GetExchange
    ResponseBase<CounterpartyContact> saveCounterpartyContactPerson(@RequestBody RequestBase<SaveCounterpartyRequest> request);

    @GetExchange
    ResponseBase<ContactPersonAddress> saveAddressContactPerson(@RequestBody RequestBase<SaveAddressContactPersonRequest> request);

    @GetExchange
    ResponseBase<CounterpartyContact> saveInternetDocument(@RequestBody RequestBase<SaveInternetDocumentRequest> request);
}