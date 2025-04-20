package org.quick.receipt.novapost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CounterpartyType {

    SENDER("Sender"),
    RECIPIENT("Recipient");

    private final String name;
}