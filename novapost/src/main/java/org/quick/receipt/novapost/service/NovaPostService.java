package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.client.NovaPostClient;
import org.quick.receipt.novapost.entity.request.*;
import org.quick.receipt.novapost.entity.response.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NovaPostService {

    private final NovaPostClient novaPostClient;

    @Value("${api.key}")
    private final String apiKey;


    public ResponseBase<City> getCities(FindByStringRequest request) {
        RequestBase<FindByStringRequest> requestBase = RequestBase.<FindByStringRequest>builder()
                .apiKey(apiKey)
                .modelName("AddressGeneral")
                .calledMethod("getCities")
                .methodProperties(request)
                .build();

        return novaPostClient.getCities(requestBase);
    }

    public ResponseBase<CounterpartySender> getCounterparties(GetCounterpartiesRequest request) {
        RequestBase<GetCounterpartiesRequest> requestBase = RequestBase.<GetCounterpartiesRequest>builder()
                .apiKey(apiKey)
                .modelName("Counterparty")
                .calledMethod("getCounterparties")
                .methodProperties(request)
                .build();

        return novaPostClient.getCounterparties(requestBase);
    }

    public ResponseBase<CargoDescription> getCargoDescriptions(FindByStringRequest request) {
        RequestBase<FindByStringRequest> requestBase = RequestBase.<FindByStringRequest>builder()
                .apiKey(apiKey)
                .modelName("Common")
                .calledMethod("getCargoDescriptionList")
                .methodProperties(request)
                .build();

        return novaPostClient.getCargoDescriptions(requestBase);
    }

    public ResponseBase<SettlementResponse> searchSettlements(SearchSettlementsRequest request) {
        RequestBase<SearchSettlementsRequest> requestBase = RequestBase.<SearchSettlementsRequest>builder()
                .apiKey(apiKey)
                .modelName("Address")
                .calledMethod("searchSettlements")
                .methodProperties(request)
                .build();

        return novaPostClient.searchSettlements(requestBase);
    }

    public ResponseBase<Warehouse> getWarehouses(GetWarehousesRequest request) {
        RequestBase<GetWarehousesRequest> requestBase = RequestBase.<GetWarehousesRequest>builder()
                .apiKey(apiKey)
                .modelName("AddressGeneral")
                .calledMethod("getWarehouses")
                .methodProperties(request)
                .build();

        return novaPostClient.getWarehouses(requestBase);
    }

    public ResponseBase<CounterpartyContact> getCounterpartyContactPersons(GetCounterpartyContactPersonsRequest request) {
        RequestBase<GetCounterpartyContactPersonsRequest> requestBase = RequestBase.<GetCounterpartyContactPersonsRequest>builder()
                .apiKey(apiKey)
                .modelName("Counterparty")
                .calledMethod("getCounterpartyContactPersons")
                .methodProperties(request)
                .build();

        return novaPostClient.getCounterpartyContactPersons(requestBase);
    }

    public ResponseBase<CounterpartyContact> saveCounterpartyContactPerson(SaveCounterpartyRequest request) {
        RequestBase<SaveCounterpartyRequest> requestBase = RequestBase.<SaveCounterpartyRequest>builder()
                .apiKey(apiKey)
                .modelName("CounterpartyGeneral")
                .calledMethod("save")
                .methodProperties(request)
                .build();

        return novaPostClient.saveCounterpartyContactPerson(requestBase);
    }

    public ResponseBase<ContactPersonAddress> saveAddressContactPerson(SaveAddressContactPersonRequest request) {
        RequestBase<SaveAddressContactPersonRequest> requestBase = RequestBase.<SaveAddressContactPersonRequest>builder()
                .apiKey(apiKey)
                .modelName("AddressContactPersonGeneral")
                .calledMethod("save")
                .methodProperties(request)
                .build();

        return novaPostClient.saveAddressContactPerson(requestBase);
    }

    public ResponseBase<InternetDocument> saveInternetDocument(SaveInternetDocumentRequest request) {
        RequestBase<SaveInternetDocumentRequest> requestBase = RequestBase.<SaveInternetDocumentRequest>builder()
                .apiKey(apiKey)
                .modelName("InternetDocumentGeneral")
                .calledMethod("save")
                .methodProperties(request)
                .build();

        return novaPostClient.saveInternetDocument(requestBase);
    }
}
