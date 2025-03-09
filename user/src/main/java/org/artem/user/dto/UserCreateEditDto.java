package org.artem.user.dto;

import lombok.Value;

@Value
public class UserCreateEditDto {

    String telegramId;
    String firstName;
    String lastName;
    String phoneNumber;
    String city;
    PostOfficeDto postOffice;
}