package org.quick.receipt.novapost.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class InternetDocumentReadDto {

    String approximateCost;
    LocalDate estimatedDelivery;
    String docNumber;
}
