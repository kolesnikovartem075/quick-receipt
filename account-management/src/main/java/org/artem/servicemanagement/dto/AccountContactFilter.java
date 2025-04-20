package org.artem.servicemanagement.dto;

public record AccountContactFilter(String apiKey,
                                   String name,
                                   String nickname,
                                   String status,
                                   Long accountId,
                                   String firstName,
                                   String lastName,
                                   String phoneNumber) {
}
