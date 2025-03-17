package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceReadDto {

    Long id;
    String name;
    String botNickname;
}