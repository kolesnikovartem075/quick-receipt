package org.artem.user.controller;

import lombok.RequiredArgsConstructor;
import org.artem.user.dto.UserProfileCreateEditDto;
import org.artem.user.dto.UserProfileFilter;
import org.artem.user.dto.UserProfileReadDto;
import org.artem.user.service.UserProfileService;
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
@RequestMapping("/api/v1/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {


    private final UserProfileService userProfileService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UserProfileReadDto> findAll(UserProfileFilter userProfileFilter, Pageable pageable) {
        return userProfileService.findAll(userProfileFilter, pageable);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserProfileReadDto findById(@PathVariable Long id) {
        return userProfileService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileReadDto create(@RequestBody UserProfileCreateEditDto userProfile) {
        return userProfileService.create(userProfile);
    }

    @PutMapping("/{id}")
    public UserProfileReadDto update(@PathVariable Long id,
                              @RequestBody UserProfileCreateEditDto userProfile) {
        return userProfileService.update(id, userProfile)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userProfileService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
