package org.artem.user.dto;

public record UserProfileFilter(String firstName,
                                String lastName,
                                String phoneNumber,
                                Long externalUserId) {
}