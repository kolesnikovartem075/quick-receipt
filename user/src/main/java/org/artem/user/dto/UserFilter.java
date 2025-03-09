package org.artem.user.dto;

public record UserFilter(String telegramId,
                         String firstName,
                         String lastName,
                         String phoneNumber) {
}