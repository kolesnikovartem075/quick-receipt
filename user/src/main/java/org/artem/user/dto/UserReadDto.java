package org.artem.user.dto;

import lombok.Builder;
import lombok.Value;
import org.artem.user.dto.nova.post.PostOfficeReadDto;

@Value
@Builder
public class UserReadDto {

    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    PostOfficeReadDto postOffice;
}