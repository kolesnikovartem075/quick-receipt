package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.database.specification.AccountSpecification;
import org.artem.servicemanagement.dto.AccountCreateEditDto;
import org.artem.servicemanagement.dto.AccountFilter;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.mapper.AccountCreateEditMapper;
import org.artem.servicemanagement.mapper.AccountReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountReadMapper accountReadMapper;
    private final AccountCreateEditMapper accountCreateEditMapper;


    public Optional<AccountReadDto> findById(Long id) {
        return accountRepository.findById(id)
                .map(accountReadMapper::map);
    }

    public Page<AccountReadDto> findAll(AccountFilter accountFilter, Pageable pageable) {
        var specification = new AccountSpecification(accountFilter);
        return accountRepository.findAll(specification, pageable)
                .map(accountReadMapper::map);
    }

    @Transactional
    public AccountReadDto create(AccountCreateEditDto accountDto) {
        return Optional.of(accountDto)
                .map(accountCreateEditMapper::map)
                .map(accountRepository::save)
                .map(accountReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AccountReadDto> update(Long id, AccountCreateEditDto accountDto) {
        return accountRepository.findById(id)
                .map(entity -> accountCreateEditMapper.map(accountDto, entity))
                .map(accountRepository::saveAndFlush)
                .map(accountReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return accountRepository.findById(id)
                .map(entity -> {
                    accountRepository.delete(entity);
                    accountRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}