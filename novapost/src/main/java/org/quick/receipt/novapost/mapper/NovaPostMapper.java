package org.quick.receipt.novapost.mapper;

import org.quick.receipt.novapost.entity.response.ResponseBase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NovaPostMapper {


    public <T> List<T> map(ResponseBase<T> responseBase) {
        return responseBase.getData();
    }
}