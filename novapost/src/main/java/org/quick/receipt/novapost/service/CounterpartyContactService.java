package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.ContactReadDto;
import org.quick.receipt.novapost.dto.CounterpartyContactDto;
import org.quick.receipt.novapost.dto.CounterpartyType;
import org.quick.receipt.novapost.entity.request.GetCounterpartyContactPersonsRequest;
import org.quick.receipt.novapost.entity.request.SaveCounterpartyRequest;
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


    public CounterpartyContact getOrCreate(String apiKey, GetCounterpartyContactPersonsRequest request, ContactReadDto userContact) {
        return novaPostService.getCounterpartyContactPersons(apiKey, request)
                .stream()
                .findFirst()
                .orElseGet(() -> createContactPerson(apiKey, userContact));
    }

    public CounterpartyContact createContactPerson(String apiKey, ContactReadDto userContact) {
        var request = SaveCounterpartyRequest.builder()
                .firstName(userContact.getFirstName())
                .lastName(userContact.getLastName())
                .phone(userContact.getPhoneNumber())
                .counterpartyType("PrivatePerson")
                .counterpartyProperty("Recipient")
                .build();

        return novaPostService.saveCounterpartyContactPerson(apiKey, request).stream()
                .findFirst()
                .orElseThrow();
    }


    public CounterpartyContact getCounterpartyContact(CounterpartyContactDto contact, CounterpartyType type) {
        return Optional.of(contact)
                .map(object -> counterpartyContactRequestMapper.map(type, object))
                .map(novaPostService::getCounterpartyContactPersons)
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .orElseThrow();
    }
}