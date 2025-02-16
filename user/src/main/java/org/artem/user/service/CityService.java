package org.artem.user.service;

import lombok.RequiredArgsConstructor;
import org.artem.user.client.NovaPostClient;
import org.artem.user.dto.nova.post.CityReadDto;
import org.artem.user.dto.nova.post.GetWarehousesRequestDto;
import org.artem.user.mapper.CityReadMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CityService {

    private final NovaPostClient novaPostClient;
    private final CityReadMapper cityReadMapper;


    public List<CityReadDto> findByQueryString(String queryString) {
        GetWarehousesRequestDto request = new GetWarehousesRequestDto();
        request.setFindByString(queryString);

        return novaPostClient.getCities(request).stream()
                .map(cityReadMapper::map)
                .toList();
    }
}