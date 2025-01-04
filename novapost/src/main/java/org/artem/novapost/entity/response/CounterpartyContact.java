package org.artem.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CounterpartyContact {

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Phones")
    private String phones;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("MiddleName")
    private String middleName;

    @JsonProperty("MarketplacePartnerDescription")
    private String marketplacePartnerDescription;
}
