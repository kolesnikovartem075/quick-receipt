package org.waybill.account.management.dto;

public record AccountFilter(
        String name,
        String nickname,
        String status,
        String externalUserId) {
}