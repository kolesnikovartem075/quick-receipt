package org.artem.servicemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.AccountContactCreateDto;
import org.artem.servicemanagement.dto.AccountContactEditDto;
import org.artem.servicemanagement.dto.AccountContactFilter;
import org.artem.servicemanagement.dto.AccountContactReadDto;
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
    public Page<AccountContactReadDto> findAll(@PathVariable String accountId,
                                               AccountContactFilter filter,
                                               Pageable pageable) {
        return accountContactService.findAll(filter, pageable);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountContactReadDto create(@RequestBody AccountContactCreateDto accountContact) {
        return accountContactService.create(accountContact);
    }

    @PutMapping("/{id}")
    public AccountContactReadDto update(@PathVariable Long id,
                                        @RequestBody AccountContactEditDto accountContact) {
        return accountContactService.update(id, accountContact)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return accountContactService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
