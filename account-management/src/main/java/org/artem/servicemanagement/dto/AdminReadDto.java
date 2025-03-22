package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminReadDto {

    Long id;
    Long externalUserId;
    AccountReadDto account;
    String role;
}