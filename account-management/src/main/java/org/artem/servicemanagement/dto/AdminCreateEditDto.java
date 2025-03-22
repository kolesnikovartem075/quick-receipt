package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class AdminCreateEditDto {

    Long externalUserId;
    Long accountId;
    String role;
}