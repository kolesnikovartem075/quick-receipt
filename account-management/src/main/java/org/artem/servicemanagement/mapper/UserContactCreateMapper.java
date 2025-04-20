package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.entity.UserContactProfile;
import org.artem.servicemanagement.database.repository.UserRepository;
import org.artem.servicemanagement.dto.UserContactCreateDto;
import org.artem.servicemanagement.service.ContactService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContactCreateMapper implements Mapper<UserContactCreateDto, UserContactProfile> {

    private final ContactService contactService;
    private final UserRepository userRepository;

    @Override
    public UserContactProfile map(UserContactCreateDto object) {
        UserContactProfile profile = new UserContactProfile();
        copy(object, profile);
        return profile;
    }

    private void copy(UserContactCreateDto object, UserContactProfile profile) {
        var user = userRepository.findById(object.getUserId()).orElseThrow();
        var contact = createContact(object);

        profile.setUser(user);
        profile.setContact(contact);
    }

    private Contact createContact(UserContactCreateDto object) {
        return contactService.create(object.getContactCreateEditDto());
    }
}