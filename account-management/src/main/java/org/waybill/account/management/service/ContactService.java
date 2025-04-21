package org.waybill.account.management.service;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.repository.ContactRepository;
import org.waybill.account.management.dto.ContactCreateEditDto;
import org.waybill.account.management.dto.ContactFilter;
import org.waybill.account.management.dto.ContactReadDto;
import org.waybill.account.management.dto.ContactSpecification;
import org.waybill.account.management.mapper.ContactCreateEditMapper;
import org.waybill.account.management.mapper.ContactReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactReadMapper contactReadMapper;
    private final ContactCreateEditMapper contactCreateEditMapper;

    public List<ContactReadDto> findAll() {
        return contactRepository.findAll().stream()
                .map(contactReadMapper::map)
                .toList();
    }

    public Optional<ContactReadDto> findById(Long id) {
        return contactRepository.findById(id)
                .map(contactReadMapper::map);
    }

    public Page<ContactReadDto> findAll(ContactFilter contactFilter, Pageable pageable) {
        var specification = new ContactSpecification(contactFilter);
        return contactRepository.findAll(specification, pageable)
                .map(contactReadMapper::map);
    }

    @Transactional
    public Contact create(ContactCreateEditDto contactDto) {
        return Optional.of(contactDto)
                .map(contactCreateEditMapper::map)
                .map(contactRepository::save)
                .orElseThrow();
    }

    @Transactional
    public Optional<Contact> update(Long id, ContactCreateEditDto contactDto) {
        return contactRepository.findById(id)
                .map(entity -> contactCreateEditMapper.map(contactDto, entity))
                .map(contactRepository::saveAndFlush);
    }

    @Transactional
    public boolean delete(Long id) {
        return contactRepository.findById(id)
                .map(entity -> {
                    contactRepository.delete(entity);
                    contactRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
