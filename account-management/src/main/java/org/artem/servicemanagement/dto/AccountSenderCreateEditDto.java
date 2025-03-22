package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class AccountSenderCreateEditDto {

    Long accountId;
    String firstName;
    String lastName;
    String phoneNumber;
    String postOfficeRef;
}