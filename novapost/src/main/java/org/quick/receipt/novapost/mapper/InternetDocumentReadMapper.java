package org.quick.receipt.novapost.mapper;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.InternetDocumentReadDto;
import org.quick.receipt.novapost.entity.response.InternetDocument;
import org.quick.receipt.novapost.util.DateUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InternetDocumentReadMapper implements Mapper<InternetDocument, InternetDocumentReadDto> {

    @Override
    public InternetDocumentReadDto map(InternetDocument object) {
        return InternetDocumentReadDto.builder()
                .approximateCost(object.getCostOnSite())
                .estimatedDelivery(DateUtils.to(object.getEstimatedDeliveryDate()))
                .docNumber(object.getIntDocNumber())
                .build();
    }
}