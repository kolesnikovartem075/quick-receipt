package org.waybill.account.management.dto;

public record UserContactFilter(Long externalUserId,
                                String role,
                                Long accountId,
                                String firstName,
                                String lastName,
                                String phoneNumber) {

}
