package org.artem.servicemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.AccountContactProfileCreateEditDto;
import org.artem.servicemanagement.dto.AccountContactProfileReadDto;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.service.AccountContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/contacts")
@RequiredArgsConstructor
public class AccountContactController {


    private final AccountContactService accountContactService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AccountReadDto> findAll(@PathVariable String accountId, Pageable pageable) {
        return accountContactService.findAll(accountId, pageable);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountContactProfileReadDto create(@RequestBody AccountContactProfileCreateEditDto accountSender) {
        return accountContactService.create(accountSender);
    }

    @PutMapping("/{id}")
    public AccountContactProfileReadDto update(@PathVariable Long id,
                                               @RequestBody AccountContactProfileCreateEditDto accountSender) {
        return accountContactService.update(id, accountSender)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return accountContactService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
