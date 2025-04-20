package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.repository.UserContactProfileRepository;
import org.artem.servicemanagement.database.specification.UserContactProfileSpecification;
import org.artem.servicemanagement.dto.UserContactCreateDto;
import org.artem.servicemanagement.dto.UserContactProfileFilter;
import org.artem.servicemanagement.dto.UserContactReadDto;
import org.artem.servicemanagement.mapper.UserContactCreateMapper;
import org.artem.servicemanagement.mapper.UserContactReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserContactService {

    private final UserContactProfileRepository userContactProfileRepository;
    private final UserContactReadMapper userContactReadMapper;
    private final UserContactCreateMapper userContactCreateMapper;

    public List<UserContactReadDto> findAll() {
        return userContactProfileRepository.findAll().stream()
                .map(userContactReadMapper::map)
                .toList();
    }

    public Page<UserContactReadDto> findAll(UserContactProfileFilter filter, Pageable pageable) {
        var specification = new UserContactProfileSpecification(filter);
        return userContactProfileRepository.findAll(specification, pageable)
                .map(userContactReadMapper::map);
    }

    public Optional<UserContactReadDto> findById(Long id) {
        return userContactProfileRepository.findById(id)
                .map(userContactReadMapper::map);
    }


    @Transactional
    public UserContactReadDto create(UserContactCreateDto dto) {
        return Optional.of(dto)
                .map(userContactCreateMapper::map)
                .map(userContactProfileRepository::save)
                .map(userContactReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserContactReadDto> update(Long id, UserContactCreateDto dto) {
        return userContactProfileRepository.findById(id)
                .map(entity -> userContactCreateMapper.map(dto, entity))
                .map(userContactProfileRepository::saveAndFlush)
                .map(userContactReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userContactProfileRepository.findById(id)
                .map(entity -> {
                    userContactProfileRepository.delete(entity);
                    userContactProfileRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
