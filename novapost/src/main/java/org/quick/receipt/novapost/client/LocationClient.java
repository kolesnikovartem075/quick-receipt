package org.quick.receipt.novapost.client;

import org.quick.receipt.novapost.entity.request.FindByStringRequest;
import org.quick.receipt.novapost.entity.request.GetWarehousesRequest;
import org.quick.receipt.novapost.entity.request.RequestBase;
import org.quick.receipt.novapost.entity.request.SearchSettlementsRequest;
import org.quick.receipt.novapost.entity.response.City;
import org.quick.receipt.novapost.entity.response.ResponseBase;
import org.quick.receipt.novapost.entity.response.SettlementResponse;
import org.quick.receipt.novapost.entity.response.Warehouse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange(url = "/v2.0/json/", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface LocationClient {

    @GetExchange
    ResponseBase<City> getCities(@RequestBody RequestBase<FindByStringRequest> request);

    @GetExchange
    ResponseBase<SettlementResponse> searchSettlements(@RequestBody RequestBase<SearchSettlementsRequest> request);

    @GetExchange
    ResponseBase<Warehouse> getWarehouses(@RequestBody RequestBase<GetWarehousesRequest> request);
}