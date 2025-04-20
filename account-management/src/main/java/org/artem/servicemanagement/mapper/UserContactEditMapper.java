package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.database.entity.UserContact;
import org.artem.servicemanagement.database.repository.UserRepository;
import org.artem.servicemanagement.dto.UserContactEditDto;
import org.artem.servicemanagement.service.ContactService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContactEditMapper implements EditMapper<UserContactEditDto, UserContact> {

    private final ContactService contactService;
    private final UserRepository userRepository;

    @Override
    public UserContact map(UserContactEditDto fromObject, UserContact toObject) {
        copy(fromObject, toObject);
        return toObject;
    }


    private void copy(UserContactEditDto object, UserContact profile) {
        var user = getUser(object);
        var contact = updateContact(object);

        profile.setUser(user);
        profile.setContact(contact);
    }

    private User getUser(UserContactEditDto object) {
        return userRepository.findById(object.getUserId())
                .orElseThrow();
    }

    private Contact updateContact(UserContactEditDto object) {
        return contactService.update(object.getContactId(), object.getContactCreateEditDto())
                .orElseThrow();
    }
}