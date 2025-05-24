package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.client.NovaPostClient;
import org.quick.receipt.novapost.entity.request.*;
import org.quick.receipt.novapost.entity.response.*;
import org.quick.receipt.novapost.mapper.NovaPostMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NovaPostService {

    private final NovaPostClient novaPostClient;
    private final NovaPostMapper novaPostMapper;

    @Value("${api.key}")
    private final String apiKey;


    public List<City> getCities(FindByStringRequest request) {
        RequestBase<FindByStringRequest> requestBase = RequestBase.<FindByStringRequest>builder()
                .apiKey(apiKey)
                .modelName("AddressGeneral")
                .calledMethod("getCities")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.getCities(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<CounterpartySender> getCounterparties(String apiKey, GetCounterpartiesRequest request) {
        RequestBase<GetCounterpartiesRequest> requestBase = RequestBase.<GetCounterpartiesRequest>builder()
                .apiKey(apiKey)
                .modelName("Counterparty")
                .calledMethod("getCounterparties")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.getCounterparties(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<CargoDescription> getCargoDescriptions(FindByStringRequest request) {
        RequestBase<FindByStringRequest> requestBase = RequestBase.<FindByStringRequest>builder()
                .apiKey(apiKey)
                .modelName("Common")
                .calledMethod("getCargoDescriptionList")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.getCargoDescriptions(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<SettlementResponse> searchSettlements(SearchSettlementsRequest request) {
        RequestBase<SearchSettlementsRequest> requestBase = RequestBase.<SearchSettlementsRequest>builder()
                .apiKey(apiKey)
                .modelName("Address")
                .calledMethod("searchSettlements")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.searchSettlements(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<Warehouse> getWarehouses(GetWarehousesRequest request) {
        RequestBase<GetWarehousesRequest> requestBase = RequestBase.<GetWarehousesRequest>builder()
                .apiKey(apiKey)
                .modelName("AddressGeneral")
                .calledMethod("getWarehouses")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.getWarehouses(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<CounterpartyContact> getCounterpartyContactPersons(String apiKey, GetCounterpartyContactPersonsRequest request) {
        RequestBase<GetCounterpartyContactPersonsRequest> requestBase = RequestBase.<GetCounterpartyContactPersonsRequest>builder()
                .apiKey(apiKey)
                .modelName("Counterparty")
                .calledMethod("getCounterpartyContactPersons")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.getCounterpartyContactPersons(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<CounterpartyContact> saveCounterpartyContactPerson(String apiKey, SaveCounterpartyRequest request) {
        RequestBase<SaveCounterpartyRequest> requestBase = RequestBase.<SaveCounterpartyRequest>builder()
                .apiKey(apiKey)
                .modelName("CounterpartyGeneral")
                .calledMethod("save")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.saveCounterpartyContactPerson(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<ContactPersonAddress> saveAddressContactPerson(SaveAddressContactPersonRequest request) {
        RequestBase<SaveAddressContactPersonRequest> requestBase = RequestBase.<SaveAddressContactPersonRequest>builder()
                .apiKey(apiKey)
                .modelName("AddressContactPersonGeneral")
                .calledMethod("save")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.saveAddressContactPerson(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }

    public List<InternetDocument> saveInternetDocument(String apiKey, SaveInternetDocumentRequest request) {
        RequestBase<SaveInternetDocumentRequest> requestBase = RequestBase.<SaveInternetDocumentRequest>builder()
                .apiKey(apiKey)
                .modelName("InternetDocumentGeneral")
                .calledMethod("save")
                .methodProperties(request)
                .build();

        return Optional.of(novaPostClient.saveInternetDocument(requestBase))
                .map(novaPostMapper::map)
                .orElseThrow();
    }
}
