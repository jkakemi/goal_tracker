package com.goaltracker.user.infrastructure.adapter;

import com.goaltracker.user.application.gateway.UserRepository;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.infrastructure.UserMapper;
import com.goaltracker.user.infrastructure.persistence.UserEntity;
import com.goaltracker.user.infrastructure.persistence.UserRepositoryJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepository {


    private final UserRepositoryJpa repository;
    private final UserMapper mapper;

    public UserRepositoryAdapter(UserRepositoryJpa repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);

        UserEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Page<User> findAllActiveTrue(Pageable pageable) {
        return repository
                .findAllActiveTrue(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
}
