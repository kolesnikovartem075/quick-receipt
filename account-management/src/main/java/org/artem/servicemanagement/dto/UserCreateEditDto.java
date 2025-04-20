package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class UserCreateEditDto {

    Long externalUserId;
    Long accountId;
    String role;
}