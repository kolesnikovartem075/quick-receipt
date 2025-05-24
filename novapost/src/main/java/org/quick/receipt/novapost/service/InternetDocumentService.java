package org.quick.receipt.novapost.service;

import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.InternetDocumentReadDto;
import org.quick.receipt.novapost.dto.InternetDocumentCreateDto;
import org.quick.receipt.novapost.mapper.InternetDocumentCreateEditMapper;
import org.quick.receipt.novapost.mapper.InternetDocumentReadMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InternetDocumentService {

    private final NovaPostService novaPostService;
    private final InternetDocumentCreateEditMapper internetDocumentCreateEditMapper;
    private final InternetDocumentReadMapper internetDocumentReadMapper;

    public InternetDocumentReadDto create(InternetDocumentCreateDto documentCreateDto) {
        return Optional.of(documentCreateDto)
                .map(internetDocumentCreateEditMapper::map)
                .map(object -> novaPostService.saveInternetDocument(documentCreateDto.getAccountContact().getApiKey(), object))
                .flatMap(objects -> objects.stream().findFirst())
                .map(internetDocumentReadMapper::map)
                .orElseThrow();
    }
}