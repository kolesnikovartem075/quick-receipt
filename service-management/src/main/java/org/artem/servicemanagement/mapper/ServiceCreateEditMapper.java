package org.artem.servicemanagement.mapper;

import org.artem.servicemanagement.database.entity.Service;
import org.artem.servicemanagement.dto.ServiceCreateEditDto;
import org.springframework.stereotype.Component;

@Component
public class ServiceCreateEditMapper implements Mapper<ServiceCreateEditDto, Service> {


    @Override
    public Service map(ServiceCreateEditDto fromObject, Service toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Service map(ServiceCreateEditDto object) {
        Service service = new Service();
        copy(object, service);

        return service;
    }

    private void copy(ServiceCreateEditDto object, Service service) {
        service.setBotNickname(object.getBotNickname());
        service.setName(object.getName());
    }
}
