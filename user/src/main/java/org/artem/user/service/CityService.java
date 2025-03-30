package org.artem.user.service;

import lombok.RequiredArgsConstructor;
import org.artem.user.client.CityClient;
import org.artem.user.dto.nova.post.CityReadDto;
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