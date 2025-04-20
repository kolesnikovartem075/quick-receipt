package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.repository.AccountContactProfileRepository;
import org.artem.servicemanagement.dto.AccountContactProfileCreateEditDto;
import org.artem.servicemanagement.dto.AccountContactProfileReadDto;
import org.artem.servicemanagement.mapper.AccountContactCreateEditMapper;
import org.artem.servicemanagement.mapper.AccountContactReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountContactService {

    private final AccountContactProfileRepository accountContactProfileRepository;
    private final AccountContactReadMapper accountContactReadMapper;
    private final AccountContactCreateEditMapper accountContactCreateEditMapper;

    public List<AccountContactProfileReadDto> findAll() {
        return accountContactProfileRepository.findAll().stream()
                .map(accountContactReadMapper::map)
                .toList();
    }


    public Page<AccountContactProfileReadDto> findAll(AccountContactProfileFilter filter, Pageable pageable) {
        var specification = new AccountContactProfileSpecification(filter);
        return accountContactProfileRepository.findAll(specification, pageable)
                .map(accountContactReadMapper::map);
    }

    public Optional<AccountContactProfileReadDto> findById(Long id) {
        return accountContactProfileRepository.findById(id)
                .map(accountContactReadMapper::map);
    }

    @Transactional
    public AccountContactProfileReadDto create(AccountContactProfileCreateEditDto dto) {
        return Optional.of(dto)
                .map(accountContactCreateEditMapper::map)
                .map(accountContactProfileRepository::save)
                .map(accountContactReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AccountContactProfileReadDto> update(Long id, AccountContactProfileCreateEditDto dto) {
        return accountContactProfileRepository.findById(id)
                .map(entity -> accountContactCreateEditMapper.map(dto, entity))
                .map(accountContactProfileRepository::saveAndFlush)
                .map(accountContactReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return accountContactProfileRepository.findById(id)
                .map(entity -> {
                    accountContactProfileRepository.delete(entity);
                    accountContactProfileRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}