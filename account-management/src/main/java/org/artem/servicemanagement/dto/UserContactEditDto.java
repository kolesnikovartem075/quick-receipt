package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class UserContactEditDto {

    Long contactId;
    Long userId;
    ContactCreateEditDto contactCreateEditDto;
}
