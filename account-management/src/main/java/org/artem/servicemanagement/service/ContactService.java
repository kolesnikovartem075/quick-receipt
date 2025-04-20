package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.repository.ContactRepository;
import org.artem.servicemanagement.dto.ContactCreateEditDto;
import org.artem.servicemanagement.dto.ContactFilter;
import org.artem.servicemanagement.dto.ContactReadDto;
import org.artem.servicemanagement.dto.ContactSpecification;
import org.artem.servicemanagement.mapper.ContactCreateEditMapper;
import org.artem.servicemanagement.mapper.ContactReadMapper;
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
