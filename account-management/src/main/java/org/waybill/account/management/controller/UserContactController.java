package org.waybill.account.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.waybill.account.management.dto.UserContactCreateDto;
import org.waybill.account.management.dto.UserContactEditDto;
import org.waybill.account.management.dto.UserContactFilter;
import org.waybill.account.management.dto.UserContactReadDto;
import org.waybill.account.management.service.UserContactService;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;


@RestController
@RequestMapping("/api/v1/accounts/{accountId}/users/{userId}/contacts")
@RequiredArgsConstructor
public class UserContactController {

    private final UserContactService userContactService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserContactReadDto findById(@PathVariable Long id,
                                       @PathVariable Long accountId,
                                       @PathVariable String userId) {
        return userContactService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UserContactReadDto> findAll(UserContactFilter filter, Pageable pageable,
                                            @PathVariable Long userId,
                                            @PathVariable Long accountId) {
        filter.setAccountId(accountId);

        return userContactService.findAll(filter, pageable);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserContactReadDto create(@RequestBody UserContactCreateDto dto,
                                     @PathVariable Long userId,
                                     @PathVariable Long accountId) {
        var contactCreateEditDto = dto.getContactCreateEditDto();
        contactCreateEditDto.setAccountId(accountId);

        return userContactService.create(new UserContactCreateDto(userId, contactCreateEditDto));
    }

    @PutMapping("/{id}")
    public UserContactReadDto update(@PathVariable Long id,
                                     @RequestBody UserContactEditDto dto,
                                     @PathVariable Long accountId,
                                     @PathVariable Long userId) {
        var contactCreateEditDto = dto.getContactCreateEditDto();
        contactCreateEditDto.setAccountId(accountId);

        return userContactService.update(id, new UserContactEditDto(dto.getContactId(), userId, contactCreateEditDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @PathVariable Long userId,
                                    @PathVariable Long accountId) {
        return userContactService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
