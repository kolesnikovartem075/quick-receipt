package org.quick.receipt.novapost.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentPropertiesDto {

    @Builder.Default
    String payerType = "Sender";

    @Builder.Default
    String paymentMethod = "Cash";

    @Builder.Default
    String cargoType = "Parcel";

    @Builder.Default
    String weight = "0.1";

    @Builder.Default
    String seatsAmount = "1";

    @Builder.Default
    String cost = "100";
}
