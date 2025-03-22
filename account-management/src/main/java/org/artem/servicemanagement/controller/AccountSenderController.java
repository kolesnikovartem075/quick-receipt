package org.artem.servicemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.AccountSenderCreateEditDto;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.service.AccountSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/account-senders")
@RequiredArgsConstructor
public class AccountSenderController {


    private final AccountSenderService accountSenderService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountSenderReadDto findById(@PathVariable Long id) {
        return accountSenderService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountSenderReadDto create(@RequestBody AccountSenderCreateEditDto accountSender) {
        return accountSenderService.create(accountSender);
    }

    @PutMapping("/{id}")
    public AccountSenderReadDto update(@PathVariable Long id,
                               @RequestBody AccountSenderCreateEditDto accountSender) {
        return accountSenderService.update(id, accountSender)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return accountSenderService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
