package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.database.entity.User;
import org.artem.user.dto.UserCreateEditDto;
import org.artem.user.dto.nova.post.CityReadDto;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.service.CityService;
import org.artem.user.service.WarehouseService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {


    private final WarehouseService warehouseService;
    private final CityService cityService;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        if (object.getPostOffice().getCityRef() == null) {
            cityService.findByQueryString(object.getCity()).stream()
                    .findFirst()
                    .map(CityReadDto::getRef)
                    .ifPresent(ref -> object.getPostOffice().setCityRef(ref));
        }

        var warehouse = getWarehouseRef(object);

        user.setTelegramId(object.getTelegramId());
        user.setFirstName(object.getFirstName());
        user.setLastName(object.getLastName());
        user.setPhoneNumber(object.getPhoneNumber());
        user.setPostOfficeRef(warehouse.getRef());
    }

    private PostOfficeReadDto getWarehouseRef(UserCreateEditDto object) {
        return warehouseService.find(object.getPostOffice())
                .orElseThrow();
    }
}