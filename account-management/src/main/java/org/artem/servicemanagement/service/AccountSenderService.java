package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.repository.AccountSenderRepository;
import org.artem.servicemanagement.dto.AccountSenderCreateEditDto;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.mapper.AccountSenderCreateEditMapper;
import org.artem.servicemanagement.mapper.AccountSenderReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountSenderService {

    private final AccountSenderRepository accountSenderRepository;
    private final AccountSenderReadMapper accountSenderReadMapper;
    private final AccountSenderCreateEditMapper accountSenderCreateEditMapper;


    public Optional<AccountSenderReadDto> findById(Long id) {
        return accountSenderRepository.findById(id)
                .map(accountSenderReadMapper::map);
    }

    @Transactional
    public AccountSenderReadDto create(AccountSenderCreateEditDto accountSenderDto) {
        return Optional.of(accountSenderDto)
                .map(accountSenderCreateEditMapper::map)
                .map(accountSenderRepository::save)
                .map(accountSenderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AccountSenderReadDto> update(Long id, AccountSenderCreateEditDto accountSenderDto) {
        return accountSenderRepository.findById(id)
                .map(entity -> accountSenderCreateEditMapper.map(accountSenderDto, entity))
                .map(accountSenderRepository::saveAndFlush)
                .map(accountSenderReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return accountSenderRepository.findById(id)
                .map(entity -> {
                    accountSenderRepository.delete(entity);
                    accountSenderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}