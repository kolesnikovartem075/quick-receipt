package org.waybill.account.management.controller;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.dto.AccountCreateEditDto;
import org.waybill.account.management.dto.AccountFilter;
import org.waybill.account.management.dto.AccountReadDto;
import org.waybill.account.management.service.AccountService;
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
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AccountReadDto> findAll(AccountFilter accountFilter, Pageable pageable) {
        return accountService.findAll(accountFilter, pageable);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountReadDto findById(@PathVariable Long id) {
        return accountService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountReadDto create(@RequestBody AccountCreateEditDto account) {
        return accountService.create(account);
    }

    @PutMapping("/{id}")
    public AccountReadDto update(@PathVariable Long id,
                                 @RequestBody AccountCreateEditDto account) {
        return accountService.update(id, account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return accountService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
