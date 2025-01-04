package org.artem.novapost.entity.request;

import lombok.Data;

@Data
public class RequestBase<T> {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private T methodProperties;
}