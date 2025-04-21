package org.waybill.account.management.dto;

import lombok.Data;

@Data
public class ContactCreateEditDto {


    Long accountId;
    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;
    String postOfficeRef;
    String city;
}