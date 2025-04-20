package org.artem.servicemanagement.dto;

import lombok.Setter;
import lombok.Value;

@Value
@Setter
public class UserContactCreateDto {

    Long userId;
    ContactCreateEditDto contactCreateEditDto;
}
