package org.waybill.account.management.service;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.client.CityClient;
import org.waybill.account.management.dto.nova.post.CityReadDto;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CityService {

    private final CityClient cityClient;


    public String getCityRef(String city) {
        return cityClient.find(city).stream()
                .findFirst()
                .map(CityReadDto::getRef)
                .orElseThrow();
    }
}