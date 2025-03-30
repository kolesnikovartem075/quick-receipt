package org.artem.servicemanagement.dto.nova.post;

import lombok.Value;

@Value
public class PostOfficeReadDto {

    String ref;
    String description;
    String shortAddress;
    String cityDescription;
}
