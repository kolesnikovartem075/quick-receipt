package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.entity.User;
import org.waybill.account.management.database.entity.UserContact;
import org.waybill.account.management.database.repository.UserRepository;
import org.waybill.account.management.dto.UserContactEditDto;
import org.waybill.account.management.service.ContactService;
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