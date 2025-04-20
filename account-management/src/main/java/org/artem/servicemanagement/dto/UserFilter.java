package org.artem.servicemanagement.dto;

public record UserFilter(String externalUserId, String accountId, String role) {
}