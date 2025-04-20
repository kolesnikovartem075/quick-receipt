package org.artem.user.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Long id;
    Long externalUserId;
}