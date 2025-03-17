package org.artem.user.dto;

import lombok.Value;
import org.artem.user.dto.nova.post.PostOfficeDto;

@Value
public class UserProfileCreateEditDto {

    Long externalUserId;
    String botNickname;
    String firstName;
    String lastName;
    String phoneNumber;
    String city;
    PostOfficeDto postOffice;
}