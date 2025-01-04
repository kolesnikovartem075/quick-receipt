package org.artem.novapost.client;

import org.artem.novapost.entity.response.ContactPersonAddress;
import org.artem.novapost.entity.response.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange(url = "/v2.0/json/", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface NovaPostClient {

    @GetExchange
    ResponseBase<City> getCities(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<CounterpartySender> getCounterparties(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<CargoDescription> getCargoDescriptions(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<SettlementResponse> searchSettlements(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<Warehouse> getWarehouses(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<CounterpartyContact> getCounterpartyContactPersons(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<CounterpartyContact> saveCounterpartyContactPerson(@RequestBody ResponseBase request);

    @GetExchange
    ResponseBase<ContactPersonAddress> saveAddressContactPerson(@RequestBody ResponseBase request);

//    @GetExchange
//    ResponseBase<CounterpartyContact> saveInternetDocument(@RequestBody ResponseBase request);


}
