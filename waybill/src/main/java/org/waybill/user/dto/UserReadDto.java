package org.waybill.user.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserReadDto {


    Long id;
    Long externalUserId;
    Long accountId;
    String role;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}