package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountSenderReadDto {

    Long id;
    AccountReadDto account;
    String firstName;
    String lastName;
    String phoneNumber;
    String postOfficeRef;
}