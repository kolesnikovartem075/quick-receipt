package org.waybill.account.management.service;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.repository.UserContactProfileRepository;
import org.waybill.account.management.database.specification.UserContactSpecification;
import org.waybill.account.management.dto.UserContactCreateDto;
import org.waybill.account.management.dto.UserContactEditDto;
import org.waybill.account.management.dto.UserContactFilter;
import org.waybill.account.management.dto.UserContactReadDto;
import org.waybill.account.management.mapper.UserContactCreateMapper;
import org.waybill.account.management.mapper.UserContactEditMapper;
import org.waybill.account.management.mapper.UserContactReadMapper;
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
    private final UserContactEditMapper userContactEditMapper;

    public List<UserContactReadDto> findAll() {
        return userContactProfileRepository.findAll().stream()
                .map(userContactReadMapper::map)
                .toList();
    }

    public Page<UserContactReadDto> findAll(UserContactFilter filter, Pageable pageable) {
        var specification = new UserContactSpecification(filter);
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
    public Optional<UserContactReadDto> update(Long id, UserContactEditDto dto) {
        return userContactProfileRepository.findById(id)
                .map(entity -> userContactEditMapper.map(dto, entity))
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
