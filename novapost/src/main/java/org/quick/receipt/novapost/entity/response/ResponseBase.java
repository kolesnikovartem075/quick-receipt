package org.quick.receipt.novapost.entity.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ResponseBase<T> {

    private boolean success;
    private List<T> data;
    private List<String> errors;
    private List<String> errorCodes;
    private List<String> warnings;
//    private List<String> info;
    private List<String> messageCodes;
    private List<String> warningCodes;
    private List<String> infoCodes;
}