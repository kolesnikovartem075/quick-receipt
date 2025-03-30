package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.client.NovaPostClient;
import org.artem.servicemanagement.dto.nova.post.CityReadDto;
import org.artem.servicemanagement.dto.nova.post.GetWarehousesRequestDto;
import org.artem.servicemanagement.mapper.CityReadMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CityService {

    private final NovaPostClient novaPostClient;
    private final CityReadMapper cityReadMapper;

    public String getCityRef(String city) {
        return findByQueryString(city).stream()
                .findFirst()
                .map(CityReadDto::getRef)
                .orElseThrow();
    }

    public List<CityReadDto> findByQueryString(String queryString) {
        GetWarehousesRequestDto request = new GetWarehousesRequestDto();
        request.setFindByString(queryString);

        return novaPostClient.getCities(request).stream()
                .map(cityReadMapper::map)
                .toList();
    }
}