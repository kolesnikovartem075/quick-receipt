package org.quick.receipt.novapost.mapper;

import org.quick.receipt.novapost.dto.CityReadDto;
import org.quick.receipt.novapost.entity.response.City;
import org.springframework.stereotype.Component;

@Component
public class CityReadMapper implements Mapper<City, CityReadDto> {


    @Override
    public CityReadDto map(City object) {
        return new CityReadDto(object.getDescription(), object.getDescriptionRu(), object.getRef());
    }
}