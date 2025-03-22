package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.repository.AdminRepository;
import org.artem.servicemanagement.dto.AdminCreateEditDto;
import org.artem.servicemanagement.dto.AdminReadDto;
import org.artem.servicemanagement.mapper.AdminCreateEditMapper;
import org.artem.servicemanagement.mapper.AdminReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminReadMapper adminReadMapper;
    private final AdminCreateEditMapper adminCreateEditMapper;


    public Optional<AdminReadDto> findById(Long id) {
        return adminRepository.findById(id)
                .map(adminReadMapper::map);
    }

    @Transactional
    public AdminReadDto create(AdminCreateEditDto adminDto) {
        return Optional.of(adminDto)
                .map(adminCreateEditMapper::map)
                .map(adminRepository::save)
                .map(adminReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AdminReadDto> update(Long id, AdminCreateEditDto adminDto) {
        return adminRepository.findById(id)
                .map(entity -> adminCreateEditMapper.map(adminDto, entity))
                .map(adminRepository::saveAndFlush)
                .map(adminReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return adminRepository.findById(id)
                .map(entity -> {
                    adminRepository.delete(entity);
                    adminRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}