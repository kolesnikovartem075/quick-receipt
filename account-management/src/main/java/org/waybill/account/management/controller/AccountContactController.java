package org.waybill.account.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.waybill.account.management.dto.AccountContactCreateDto;
import org.waybill.account.management.dto.AccountContactEditDto;
import org.waybill.account.management.dto.AccountContactFilter;
import org.waybill.account.management.dto.AccountContactReadDto;
import org.waybill.account.management.service.AccountContactService;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/contacts")
@RequiredArgsConstructor
public class AccountContactController {


    private final AccountContactService accountContactService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountContactReadDto findById(@PathVariable Long id,
                                          @PathVariable Long accountId) {
        return accountContactService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AccountContactReadDto> findAll(@PathVariable Long accountId,
                                               AccountContactFilter filter,
                                               Pageable pageable) {
        filter.setAccountId(accountId);
        return accountContactService.findAll(filter, pageable);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountContactReadDto create(@PathVariable Long accountId,
                                        @RequestBody AccountContactCreateDto accountContact) {
        accountContact.setAccountId(accountId);
        return accountContactService.create(accountContact);
    }

    @PutMapping("/{id}")
    public AccountContactReadDto update(@PathVariable Long id,
                                        @PathVariable Long accountId,
                                        @RequestBody AccountContactEditDto accountContact) {
        accountContact.setAccountId(accountId);
        return accountContactService.update(id, accountContact)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @PathVariable String accountId) {
        return accountContactService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
