package org.artem.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CounterpartySender {

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Ref")
    private String ref;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Counterparty")
    private String counterparty;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("MiddleName")
    private String middleName;

    @JsonProperty("CounterpartyFullName")
    private String counterpartyFullName;

    @JsonProperty("OwnershipFormRef")
    private String ownershipFormRef;

    @JsonProperty("OwnershipFormDescription")
    private String ownershipFormDescription;

    @JsonProperty("EDRPOU")
    private String edrpou;

    @JsonProperty("CounterpartyType")
    private String counterpartyType;

    @JsonProperty("MarketplacePartnerDescription")
    private String marketplacePartnerDescription;

    @JsonProperty("CityDescription")
    private String cityDescription;

    @JsonProperty("ContactPerson")
    private ContactPersonResponse contactPerson;
}
