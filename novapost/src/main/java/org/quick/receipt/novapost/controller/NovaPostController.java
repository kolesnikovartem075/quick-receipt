package org.quick.receipt.novapost.controller;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.entity.request.*;
import org.quick.receipt.novapost.entity.response.*;
import org.quick.receipt.novapost.service.NovaPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nova-post")
@RequiredArgsConstructor
public class NovaPostController {


    private final NovaPostService novaPostService;

    @PostMapping("/cities")
    public List<City> getCities(@RequestBody FindByStringRequest request) {
        return novaPostService.getCities(request);
    }

    @PostMapping("/counterparties")
    public List<CounterpartySender> getCounterparties(@RequestBody GetCounterpartiesRequest request) {
        return novaPostService.getCounterparties(request);
    }

    @PostMapping("/cargo-descriptions")
    public List<CargoDescription> getCargoDescriptions(@RequestBody FindByStringRequest request) {
        return novaPostService.getCargoDescriptions(request);
    }

    @PostMapping("/settlements")
    public List<SettlementResponse> searchSettlements(@RequestBody SearchSettlementsRequest request) {
        return novaPostService.searchSettlements(request);
    }

    @PostMapping("/warehouses")
    public List<Warehouse> getWarehouses(@RequestBody GetWarehousesRequest request) {
        var warehouses = novaPostService.getWarehouses(request);
        System.out.println(warehouses);
        return warehouses;
    }

    @PostMapping("/counterparty-contact-persons")
    public List<CounterpartyContact> getCounterpartyContactPersons(@RequestBody GetCounterpartyContactPersonsRequest request) {
        return novaPostService.getCounterpartyContactPersons(request);
    }

    @PostMapping("/save-counterparty-contact-person")
    public List<CounterpartyContact> saveCounterpartyContactPerson(@RequestBody SaveCounterpartyRequest request) {
        return novaPostService.saveCounterpartyContactPerson(request);
    }

    @PostMapping("/save-address-contact-person")
    public List<ContactPersonAddress> saveAddressContactPerson(@RequestBody SaveAddressContactPersonRequest request) {
        return novaPostService.saveAddressContactPerson(request);
    }

    @PostMapping("/save-internet-document")
    public List<InternetDocument> saveInternetDocument(@RequestBody SaveInternetDocumentRequest request,
                                                       @RequestHeader String token) {
        return novaPostService.saveInternetDocument(token, request);
    }
}