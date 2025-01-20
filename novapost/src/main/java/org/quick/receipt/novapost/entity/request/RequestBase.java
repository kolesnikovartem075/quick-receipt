package org.quick.receipt.novapost.entity.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestBase<T> {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private T methodProperties;
}