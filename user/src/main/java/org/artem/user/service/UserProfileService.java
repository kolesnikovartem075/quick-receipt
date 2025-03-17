package org.artem.user.service;

import lombok.RequiredArgsConstructor;
import org.artem.user.database.repository.UserProfileRepository;
import org.artem.user.database.specification.UserProfileSpecification;
import org.artem.user.dto.UserProfileCreateEditDto;
import org.artem.user.dto.UserProfileFilter;
import org.artem.user.dto.UserProfileReadDto;
import org.artem.user.mapper.UserProfileCreateEditMapper;
import org.artem.user.mapper.UserProfileReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileReadMapper userProfileReadMapper;
    private final UserProfileCreateEditMapper userProfileCreateEditMapper;


    public List<UserProfileReadDto> findAll() {
        return userProfileRepository.findAll().stream()
                .map(userProfileReadMapper::map)
                .toList();
    }

    public Optional<UserProfileReadDto> findById(Long id) {
        return userProfileRepository.findById(id)
                .map(userProfileReadMapper::map);
    }

    public Page<UserProfileReadDto> findAll(UserProfileFilter userProfileFilter, Pageable pageable) {
        var specification = new UserProfileSpecification(userProfileFilter);
        return userProfileRepository.findAll(specification, pageable)
                .map(userProfileReadMapper::map);
    }

    @Transactional
    public UserProfileReadDto create(UserProfileCreateEditDto userProfileDto) {
        return Optional.of(userProfileDto)
                .map(userProfileCreateEditMapper::map)
                .map(userProfileRepository::save)
                .map(userProfileReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserProfileReadDto> update(Long id, UserProfileCreateEditDto userProfileDto) {
        return userProfileRepository.findById(id)
                .map(entity -> userProfileCreateEditMapper.map(userProfileDto, entity))
                .map(userProfileRepository::saveAndFlush)
                .map(userProfileReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userProfileRepository.findById(id)
                .map(entity -> {
                    userProfileRepository.delete(entity);
                    userProfileRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}