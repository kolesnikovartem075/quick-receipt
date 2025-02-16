package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.dto.UserReadDto;
import org.artem.user.database.entity.User;
import org.artem.user.service.WarehouseService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final WarehouseService warehouseService;

    @Override
    public UserReadDto map(User object) {
        var warehouse = getWarehouse(object);

        return UserReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .phoneNumber(object.getPhoneNumber())
                .postOffice(warehouse)
                .build();
    }

    private PostOfficeReadDto getWarehouse(User object) {
        return warehouseService.findByRef(object.getPostOfficeRef()).orElseThrow();
    }
}