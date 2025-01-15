package org.quick.receipt.novapost.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaveInternetDocumentRequest {

    @JsonProperty("PayerType")
    private String payerType;

    @JsonProperty("PaymentMethod")
    private String paymentMethod;

    @JsonProperty("DateTime")
    private String dateTime;

    @JsonProperty("CargoType")
    private String cargoType;

    @JsonProperty("Weight")
    private String weight;

    @JsonProperty("ServiceType")
    private String serviceType;

    @JsonProperty("SeatsAmount")
    private String seatsAmount;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Cost")
    private String cost;

    @JsonProperty("CitySender")
    private String citySender;

    @JsonProperty("Sender")
    private String sender;

    @JsonProperty("SenderAddress")
    private String senderAddress;

    @JsonProperty("ContactSender")
    private String contactSender;

    @JsonProperty("SendersPhone")
    private String sendersPhone;

    @JsonProperty("CityRecipient")
    private String cityRecipient;

    @JsonProperty("Recipient")
    private String recipient;

    @JsonProperty("RecipientAddress")
    private String recipientAddress;

    @JsonProperty("ContactRecipient")
    private String contactRecipient;

    @JsonProperty("RecipientsPhone")
    private String recipientsPhone;
}
