package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class AccountCreateEditDto {

    String name;
    String nickname;
    String status;
}