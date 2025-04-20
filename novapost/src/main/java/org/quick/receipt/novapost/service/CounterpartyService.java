package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.CounterpartyContactDto;
import org.quick.receipt.novapost.dto.CounterpartyType;
import org.quick.receipt.novapost.entity.request.GetCounterpartiesRequest;
import org.quick.receipt.novapost.entity.response.CounterpartySender;
import org.quick.receipt.novapost.mapper.CounterpartyContactRequestMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterpartyService {

    private final NovaPostService novaPostService;
    private final CounterpartyContactRequestMapper counterpartyContactRequestMapper;


    public String getCounterparty(CounterpartyType type) {
        return novaPostService.getCounterparties(GetCounterpartiesRequest.of(type))
                .stream().findFirst()
                .map(CounterpartySender::getCounterparty)
                .orElseThrow();
    }

    public String getCounterpartyContact(CounterpartyContactDto contact, CounterpartyType type) {


//        Optional.of(contact)
//                .map(counterpartyContactRequestMapper::map)
//                .map(contact -> counterpartyContactRequestMapper.map(type, contact))

//        novaPostService.getCounterpartyContactPersons();
        return null;
    }
}