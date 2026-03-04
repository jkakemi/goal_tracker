package com.goaltracker.gamification.infrastructure.adapter;

import com.goaltracker.gamification.application.gateway.UserProfileRepository;
import com.goaltracker.gamification.domain.UserProfile;
import com.goaltracker.gamification.infrastructure.UserProfileMapper;
import com.goaltracker.gamification.infrastructure.persistence.UserProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserProfileRepositoryAdapter implements UserProfileRepository {

    private final UserProfileJpaRepository jpaRepository;
    private final UserProfileMapper mapper;

    public UserProfileRepositoryAdapter(UserProfileJpaRepository jpaRepository, UserProfileMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserProfile> findById(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(mapper::toDomain);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        var entity = mapper.toEntity(userProfile);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}