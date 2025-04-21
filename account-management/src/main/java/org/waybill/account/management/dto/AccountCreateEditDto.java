package org.waybill.account.management.dto;

import lombok.Value;

@Value
public class AccountCreateEditDto {

    String name;
    String nickname;
    String status;
}