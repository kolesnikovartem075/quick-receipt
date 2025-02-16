package org.artem.user.mapper;

import org.artem.user.dto.nova.post.City;
import org.artem.user.dto.nova.post.CityReadDto;
import org.springframework.stereotype.Component;

@Component
public class CityReadMapper implements Mapper<City, CityReadDto> {


    @Override
    public CityReadDto map(City object) {
        return new CityReadDto(object.getDescription(), object.getDescriptionRu(), object.getRef());
    }
}
