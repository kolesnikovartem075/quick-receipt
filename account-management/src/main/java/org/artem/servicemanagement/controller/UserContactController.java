package org.artem.servicemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.UserContactCreateDto;
import org.artem.servicemanagement.dto.UserContactEditDto;
import org.artem.servicemanagement.dto.UserContactFilter;
import org.artem.servicemanagement.dto.UserContactReadDto;
import org.artem.servicemanagement.service.UserContactService;
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
@RequestMapping("/api/v1/accounts/{accountId}/users/{userId}/contacts")
@RequiredArgsConstructor
public class UserContactController {

    private final UserContactService userContactService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UserContactReadDto> findAll(UserContactFilter filter, Pageable pageable, @PathVariable Long userId) {
        return userContactService.findAll(filter, pageable);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserContactReadDto create(@RequestBody UserContactCreateDto dto,
                                     @PathVariable Long userId,
                                     @PathVariable String accountId) {
        return userContactService.create(dto);
    }

    @PutMapping("/{id}")
    public UserContactReadDto update(@PathVariable Long id,
                                     @RequestBody UserContactEditDto dto,
                                     @PathVariable String accountId) {
        return userContactService.update(id, dto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @PathVariable String userId,
                                    @PathVariable String accountId) {
        return userContactService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
