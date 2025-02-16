package org.artem.user.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String telegramId;
    String firstName;
    String lastName;
    String phoneNumber;
    //    String addressRef;
    String postOfficeQuery;
    String cityRef;
}