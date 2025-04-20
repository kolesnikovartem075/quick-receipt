package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class ContactCreateEditDto {


    Long accountId;
    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;
    String postOfficeRef;
    String cityRef;
}