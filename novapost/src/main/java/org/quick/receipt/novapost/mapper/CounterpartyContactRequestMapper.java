package org.quick.receipt.novapost.mapper;

import lombok.AllArgsConstructor;
import org.quick.receipt.novapost.dto.CounterpartyContactDto;
import org.quick.receipt.novapost.dto.CounterpartyType;
import org.quick.receipt.novapost.entity.request.GetCounterpartyContactPersonsRequest;
import org.quick.receipt.novapost.service.CounterpartyService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CounterpartyContactRequestMapper {

    public GetCounterpartyContactPersonsRequest map(CounterpartyType type, CounterpartyContactDto object) {
        return GetCounterpartyContactPersonsRequest.builder()
//                .ref(counterparty)
                .findByString(object.getPhoneNumber())
                .build();
    }
}