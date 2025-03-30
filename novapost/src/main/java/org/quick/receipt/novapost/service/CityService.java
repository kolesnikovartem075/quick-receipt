package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.CityReadDto;
import org.quick.receipt.novapost.entity.request.FindByStringRequest;
import org.quick.receipt.novapost.mapper.CityReadMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CityService {

    private final NovaPostService novaPostService;
    private final CityReadMapper cityReadMapper;


    public String getCityRef(String queryString) {
        return findByQueryString(queryString).stream()
                .findFirst()
                .map(CityReadDto::getRef)
                .orElseThrow();
    }

    public List<CityReadDto> findByQueryString(String queryString) {
        FindByStringRequest request = new FindByStringRequest();
        request.setFindByString(queryString);

        return novaPostService.getCities(request).stream()
                .map(cityReadMapper::map)
                .toList();
    }
}