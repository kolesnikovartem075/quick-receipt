package org.artem.servicemanagement.dto;

public record UserContactFilter(Long externalUserId,
                                String role,
                                Long accountId,
                                String firstName,
                                String lastName,
                                String phoneNumber) {

}
