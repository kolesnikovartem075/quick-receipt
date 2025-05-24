package org.quick.receipt.novapost.controller;


import lombok.RequiredArgsConstructor;
import org.quick.receipt.novapost.dto.InternetDocumentCreateDto;
import org.quick.receipt.novapost.dto.InternetDocumentReadDto;
import org.quick.receipt.novapost.service.InternetDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internet-document")
@RequiredArgsConstructor
public class InternetDocumentController {

    private final InternetDocumentService internetDocumentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InternetDocumentReadDto create(@RequestBody InternetDocumentCreateDto documentCreateDto) {
        return internetDocumentService.create(documentCreateDto);
    }
}
