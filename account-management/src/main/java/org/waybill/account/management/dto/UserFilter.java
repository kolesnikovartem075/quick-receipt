package org.waybill.account.management.dto;

public record UserFilter(String externalUserId, String accountId, String role) {
}