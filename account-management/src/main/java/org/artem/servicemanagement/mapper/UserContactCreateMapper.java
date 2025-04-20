package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.database.entity.UserContact;
import org.artem.servicemanagement.database.repository.UserRepository;
import org.artem.servicemanagement.dto.UserContactCreateDto;
import org.artem.servicemanagement.service.ContactService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContactCreateMapper implements CreateMapper<UserContactCreateDto, UserContact> {

    private final ContactService contactService;
    private final UserRepository userRepository;

    @Override
    public UserContact map(UserContactCreateDto object) {
        UserContact profile = new UserContact();
        copy(object, profile);
        return profile;
    }

    private void copy(UserContactCreateDto object, UserContact profile) {
        var user = getUser(object);
        var contact = createContact(object);

        profile.setUser(user);
        profile.setContact(contact);
    }

    private User getUser(UserContactCreateDto object) {
        return userRepository.findById(object.getUserId()).orElseThrow();
    }

    private Contact createContact(UserContactCreateDto object) {
        return contactService.create(object.getContactCreateEditDto());
    }
}