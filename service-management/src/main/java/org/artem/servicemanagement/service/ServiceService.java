package org.artem.servicemanagement.service;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.repository.ServiceRepository;
import org.artem.servicemanagement.database.specification.ServiceSpecification;
import org.artem.servicemanagement.dto.ServiceCreateEditDto;
import org.artem.servicemanagement.dto.ServiceFilter;
import org.artem.servicemanagement.dto.ServiceReadDto;
import org.artem.servicemanagement.mapper.ServiceCreateEditMapper;
import org.artem.servicemanagement.mapper.ServiceReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceReadMapper serviceReadMapper;
    private final ServiceCreateEditMapper serviceCreateEditMapper;


    public Optional<ServiceReadDto> findById(Long id) {
        return serviceRepository.findById(id)
                .map(serviceReadMapper::map);
    }

    public Page<ServiceReadDto> findAll(ServiceFilter serviceFilter, Pageable pageable) {
        var specification = new ServiceSpecification(serviceFilter);
        return serviceRepository.findAll(specification, pageable)
                .map(serviceReadMapper::map);
    }

    @Transactional
    public ServiceReadDto create(ServiceCreateEditDto serviceDto) {
        return Optional.of(serviceDto)
                .map(serviceCreateEditMapper::map)
                .map(serviceRepository::save)
                .map(serviceReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ServiceReadDto> update(Long id, ServiceCreateEditDto serviceDto) {
        return serviceRepository.findById(id)
                .map(entity -> serviceCreateEditMapper.map(serviceDto, entity))
                .map(serviceRepository::saveAndFlush)
                .map(serviceReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return serviceRepository.findById(id)
                .map(entity -> {
                    serviceRepository.delete(entity);
                    serviceRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}