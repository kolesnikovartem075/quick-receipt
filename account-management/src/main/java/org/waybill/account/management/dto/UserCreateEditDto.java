package org.waybill.account.management.dto;

import lombok.Value;

@Value
public class UserCreateEditDto {

    Long externalUserId;
    Long accountId;
    String role;
}