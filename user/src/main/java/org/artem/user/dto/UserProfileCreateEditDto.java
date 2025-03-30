package org.artem.user.dto;

import lombok.Value;
import org.artem.user.dto.nova.post.WarehouseRequestDto;

@Value
public class UserProfileCreateEditDto {

    Long externalUserId;
    String botNickname;
    String firstName;
    String lastName;
    String phoneNumber;
    String city;
    WarehouseRequestDto postOffice;
}