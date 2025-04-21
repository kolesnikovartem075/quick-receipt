package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.entity.User;
import org.waybill.account.management.database.entity.UserContact;
import org.waybill.account.management.database.repository.UserRepository;
import org.waybill.account.management.dto.UserContactCreateDto;
import org.waybill.account.management.service.ContactService;
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