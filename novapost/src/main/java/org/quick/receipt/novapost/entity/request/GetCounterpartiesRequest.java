package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.quick.receipt.novapost.dto.CounterpartyType;

@Data
@AllArgsConstructor
public class GetCounterpartiesRequest {

    @JsonProperty("CounterpartyProperty")
    private String counterpartyProperty;

    @JsonProperty("GetPrivatePerson")
    private String getPrivatePerson;


    public static GetCounterpartiesRequest of(CounterpartyType counterpartyType) {
        if (counterpartyType.equals(CounterpartyType.SENDER)) {
            return new GetCounterpartiesRequest(counterpartyType.getName(), "1");
        }

        return new GetCounterpartiesRequest(counterpartyType.getName(), null);
    }
}
