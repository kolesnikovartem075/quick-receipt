package org.artem.servicemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.dto.AdminCreateEditDto;
import org.artem.servicemanagement.dto.AdminReadDto;
import org.artem.servicemanagement.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminReadDto findById(@PathVariable Long id) {
        return adminService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AdminReadDto create(@RequestBody AdminCreateEditDto admin) {
        return adminService.create(admin);
    }

    @PutMapping("/{id}")
    public AdminReadDto update(@PathVariable Long id,
                               @RequestBody AdminCreateEditDto admin) {
        return adminService.update(id, admin)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return adminService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
