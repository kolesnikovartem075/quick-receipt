package org.artem.servicemanagement.dto;

import lombok.Value;
import org.artem.servicemanagement.dto.nova.post.PostOfficeRequestDto;

@Value
public class AccountSenderCreateEditDto {

    Long accountId;
    String firstName;
    String lastName;
    String phoneNumber;
    String city;
    PostOfficeRequestDto postOffice;
}