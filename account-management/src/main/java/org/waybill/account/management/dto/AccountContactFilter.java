package org.waybill.account.management.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class AccountContactFilter {

    String apiKey;
    String name;
    String nickname;
    String status;
    Long accountId;
    String firstName;
    String lastName;
    String phoneNumber;
}
