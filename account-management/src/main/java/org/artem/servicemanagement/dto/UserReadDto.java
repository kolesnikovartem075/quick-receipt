package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;
import org.artem.servicemanagement.database.entity.UserRole;

import java.time.LocalDateTime;

@Value
@Builder
public class UserReadDto {


    Long id;
    String externalUserId;
    AccountReadDto account;
    UserRole role;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}