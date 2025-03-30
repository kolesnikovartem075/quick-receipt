package org.artem.user.dto;

import lombok.Builder;
import lombok.Value;
import org.artem.user.dto.nova.post.PostOfficeReadDto;

@Value
@Builder
public class UserProfileReadDto {

    Long id;
    Long externalUserId;
    Long accountId;
    String firstName;
    String lastName;
    String phoneNumber;
    PostOfficeReadDto postOffice;
}
