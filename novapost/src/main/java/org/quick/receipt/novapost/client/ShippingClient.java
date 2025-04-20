package org.quick.receipt.novapost.client;

import org.quick.receipt.novapost.entity.request.FindByStringRequest;
import org.quick.receipt.novapost.entity.request.RequestBase;
import org.quick.receipt.novapost.entity.request.SaveInternetDocumentRequest;
import org.quick.receipt.novapost.entity.response.CargoDescription;
import org.quick.receipt.novapost.entity.response.InternetDocument;
import org.quick.receipt.novapost.entity.response.ResponseBase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange(url = "/v2.0/json/", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface ShippingClient {

    @GetExchange
    ResponseBase<CargoDescription> getCargoDescriptions(@RequestBody RequestBase<FindByStringRequest> request);

    @GetExchange
    ResponseBase<InternetDocument> saveInternetDocument(@RequestBody RequestBase<SaveInternetDocumentRequest> request);
}