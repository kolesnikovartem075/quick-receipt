package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.CounterpartyContactDto;
import org.quick.receipt.novapost.dto.CounterpartyType;
import org.quick.receipt.novapost.entity.response.CounterpartyContact;
import org.quick.receipt.novapost.mapper.CounterpartyContactRequestMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CounterpartyContactService {

    private final NovaPostService novaPostService;
    private final CounterpartyContactRequestMapper counterpartyContactRequestMapper;


    public CounterpartyContact getCounterpartyContact(CounterpartyContactDto contact, CounterpartyType type) {
        return Optional.of(contact)
                .map(object -> counterpartyContactRequestMapper.map(type, object))
                .map(novaPostService::getCounterpartyContactPersons)
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .orElseThrow();
    }
}