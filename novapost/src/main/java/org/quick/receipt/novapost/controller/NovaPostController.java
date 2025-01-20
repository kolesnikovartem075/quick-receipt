package org.quick.receipt.novapost.controller;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.entity.request.*;
import org.quick.receipt.novapost.entity.response.*;
import org.quick.receipt.novapost.service.NovaPostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/novapost")
@RequiredArgsConstructor
public class NovaPostController {


    private final NovaPostService novaPostService;

    @PostMapping("/cities")
    public ResponseBase<City> getCities(@RequestBody FindByStringRequest request) {
        return novaPostService.getCities(request);
    }

    @PostMapping("/counterparties")
    public ResponseBase<CounterpartySender> getCounterparties(@RequestBody GetCounterpartiesRequest request) {
        return novaPostService.getCounterparties(request);
    }

    @PostMapping("/cargo-descriptions")
    public ResponseBase<CargoDescription> getCargoDescriptions(@RequestBody FindByStringRequest request) {
        return novaPostService.getCargoDescriptions(request);
    }

    @PostMapping("/settlements")
    public ResponseBase<SettlementResponse> searchSettlements(@RequestBody SearchSettlementsRequest request) {
        return novaPostService.searchSettlements(request);
    }

    @PostMapping("/warehouses")
    public ResponseBase<Warehouse> getWarehouses(@RequestBody GetWarehousesRequest request) {
        return novaPostService.getWarehouses(request);
    }

    @PostMapping("/counterparty-contact-persons")
    public ResponseBase<CounterpartyContact> getCounterpartyContactPersons(@RequestBody GetCounterpartyContactPersonsRequest request) {
        return novaPostService.getCounterpartyContactPersons(request);
    }

    @PostMapping("/save-counterparty-contact-person")
    public ResponseBase<CounterpartyContact> saveCounterpartyContactPerson(@RequestBody SaveCounterpartyRequest request) {
        return novaPostService.saveCounterpartyContactPerson(request);
    }

    @PostMapping("/save-address-contact-person")
    public ResponseBase<ContactPersonAddress> saveAddressContactPerson(@RequestBody SaveAddressContactPersonRequest request) {
        return novaPostService.saveAddressContactPerson(request);
    }

    @PostMapping("/save-internet-document")
    public ResponseBase<InternetDocument> saveInternetDocument(@RequestBody SaveInternetDocumentRequest request) {
        return novaPostService.saveInternetDocument(request);
    }
}