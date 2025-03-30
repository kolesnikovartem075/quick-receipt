package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.database.entity.UserProfile;
import org.artem.user.dto.UserProfileReadDto;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.service.WarehouseService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserProfileReadMapper implements Mapper<UserProfile, UserProfileReadDto> {

    private final WarehouseService warehouseService;

    @Override
    public UserProfileReadDto map(UserProfile object) {
        var user = object.getUser();
        var warehouse = getWarehouse(object);

        return UserProfileReadDto.builder()
                .id(object.getId())
                .externalUserId(user.getExternalUserId())
                .accountId(object.getAccountId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .phoneNumber(object.getPhoneNumber())
                .postOffice(warehouse)
                .build();
    }

    private PostOfficeReadDto getWarehouse(UserProfile object) {
        return warehouseService.findByRef(object.getPostOfficeRef()).orElseThrow();
    }
}
