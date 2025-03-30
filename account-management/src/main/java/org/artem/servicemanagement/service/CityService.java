package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.client.CityClient;
import org.artem.servicemanagement.dto.nova.post.CityReadDto;
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