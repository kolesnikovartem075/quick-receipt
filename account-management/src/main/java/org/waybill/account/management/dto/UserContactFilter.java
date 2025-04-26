package org.waybill.account.management.dto;

import lombok.Data;

@Data
public class UserContactFilter {

    Long externalUserId;
    String role;
    Long accountId;
    String firstName;
    String lastName;
    String phoneNumber;

}
