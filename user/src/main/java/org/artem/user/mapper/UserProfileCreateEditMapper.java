package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.database.entity.User;
import org.artem.user.database.entity.UserProfile;
import org.artem.user.database.repository.UserRepository;
import org.artem.user.dto.UserCreateEditDto;
import org.artem.user.dto.UserProfileCreateEditDto;
import org.artem.user.dto.UserReadDto;
import org.artem.user.dto.nova.post.CityReadDto;
import org.artem.user.dto.nova.post.PostOfficeReadDto;
import org.artem.user.service.CityService;
import org.artem.user.service.UserService;
import org.artem.user.service.WarehouseService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserProfileCreateEditMapper implements Mapper<UserProfileCreateEditDto, UserProfile> {


    private final UserService userService;
    private final UserRepository userRepository;
    private final WarehouseService warehouseService;
    private final CityService cityService;

    @Override
    public UserProfile map(UserProfileCreateEditDto fromObject, UserProfile toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public UserProfile map(UserProfileCreateEditDto object) {
        UserProfile user = new UserProfile();
        copy(object, user);

        return user;
    }

    private void copy(UserProfileCreateEditDto object, UserProfile userProfile) {
        if (object.getPostOffice().getCityRef() == null) {
            cityService.findByQueryString(object.getCity()).stream()
                    .findFirst()
                    .map(CityReadDto::getRef)
                    .ifPresent(ref -> object.getPostOffice().setCityRef(ref));
        }

        var warehouse = getWarehouseRef(object);
        var user = getUser(object);

        userProfile.setUser(user);
        userProfile.setFirstName(object.getFirstName());
        userProfile.setLastName(object.getLastName());
        userProfile.setPhoneNumber(object.getPhoneNumber());
        userProfile.setPostOfficeRef(warehouse.getRef());
    }

    private User getUser(UserProfileCreateEditDto object) {
        return userRepository.findByExternalUserId(object.getExternalUserId())
                .orElseGet(() -> createUser(object.getExternalUserId()));
    }

    private User createUser(Long externalUserId) {
        return Optional.of(new UserCreateEditDto(externalUserId))
                .map(userService::create)
                .map(UserReadDto::getId)
                .flatMap(userRepository::findById)
                .orElseThrow();
    }

    private PostOfficeReadDto getWarehouseRef(UserProfileCreateEditDto object) {
        return warehouseService.find(object.getPostOffice())
                .orElseThrow();
    }
}