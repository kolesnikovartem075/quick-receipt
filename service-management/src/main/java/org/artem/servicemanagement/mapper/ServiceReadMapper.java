package org.artem.servicemanagement.mapper;

import org.artem.order.mapper.Mapper;
import org.artem.servicemanagement.database.entity.Service;
import org.artem.servicemanagement.dto.ServiceReadDto;
import org.springframework.stereotype.Component;

@Component
public class ServiceReadMapper implements Mapper<Service, ServiceReadDto> {


    @Override
    public ServiceReadDto map(Service object) {
        return ServiceReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .botNickname(object.getBotNickname())
                .build();
    }
}
