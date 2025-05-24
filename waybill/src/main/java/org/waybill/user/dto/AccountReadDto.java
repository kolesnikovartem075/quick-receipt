package org.waybill.user.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AccountReadDto {

    Long id;
    String name;
    String nickname;
    String status;
    List<AccountContactReadDto> contactProfiles;
}