package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.CounterpartyType;
import org.quick.receipt.novapost.entity.request.GetCounterpartiesRequest;
import org.quick.receipt.novapost.entity.response.CounterpartySender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterpartyService {

    private final NovaPostService novaPostService;

    public String getSenderRef(String apiKey) {
        return novaPostService.getCounterparties(apiKey, GetCounterpartiesRequest.of(CounterpartyType.SENDER))
                .stream().findFirst()
                .map(CounterpartySender::getCounterparty)
                .orElseThrow();
    }
}