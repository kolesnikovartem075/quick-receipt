package org.waybill.account.management.service;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.repository.AccountContactProfileRepository;
import org.waybill.account.management.database.specification.AccountContactSpecification;
import org.waybill.account.management.dto.AccountContactCreateDto;
import org.waybill.account.management.dto.AccountContactEditDto;
import org.waybill.account.management.dto.AccountContactFilter;
import org.waybill.account.management.dto.AccountContactReadDto;
import org.waybill.account.management.mapper.AccountContactCreateMapper;
import org.waybill.account.management.mapper.AccountContactEditMapper;
import org.waybill.account.management.mapper.AccountContactReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AccountContactCreateMapper accountContactCreateMapper;
    private final AccountContactEditMapper accountContactEditMapper;

    public List<AccountContactReadDto> findAll() {
        return accountContactProfileRepository.findAll().stream()
                .map(accountContactReadMapper::map)
                .toList();
    }


    public Page<AccountContactReadDto> findAll(AccountContactFilter filter, Pageable pageable) {
        var specification = new AccountContactSpecification(filter);
        return accountContactProfileRepository.findAll(specification, pageable)
                .map(accountContactReadMapper::map);
    }

    public Optional<AccountContactReadDto> findById(Long id) {
        return accountContactProfileRepository.findById(id)
                .map(accountContactReadMapper::map);
    }

    @Transactional
    public AccountContactReadDto create(AccountContactCreateDto dto) {
        return Optional.of(dto)
                .map(accountContactCreateMapper::map)
                .map(accountContactProfileRepository::save)
                .map(accountContactReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AccountContactReadDto> update(Long id, AccountContactEditDto dto) {
        return accountContactProfileRepository.findById(id)
                .map(entity -> accountContactEditMapper.map(dto, entity))
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