package org.waybill.user.dto.nova.post;

import lombok.Value;

import java.time.LocalDate;

@Value
public class InternetDocumentReadDto {

    String approximateCost;
    LocalDate estimatedDelivery;
    String docNumber;
}
