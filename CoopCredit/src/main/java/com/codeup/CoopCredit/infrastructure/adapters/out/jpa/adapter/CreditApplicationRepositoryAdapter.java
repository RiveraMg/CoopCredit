package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.adapter;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;
import com.codeup.CoopCredit.domain.ports.out.ICreditApplicationRepositoryPort;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.CreditApplicationEntity;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper.CreditApplicationEntityMapper;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository.ICreditApplicationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditApplicationRepositoryAdapter implements ICreditApplicationRepositoryPort {

    private final ICreditApplicationJpaRepository repository;
    private final CreditApplicationEntityMapper mapper;

    @Override
    public CreditApplication save(CreditApplication application) {
        log.debug("Saving credit application for member: {}", application.getMemberId());
        CreditApplicationEntity entity = mapper.toEntity(application);
        CreditApplicationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CreditApplication> findById(Long id) {
        log.debug("Finding credit application by id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<CreditApplication> findByMemberId(Long memberId) {
        log.debug("Finding credit applications by member id: {}", memberId);
        return repository.findByMemberId(memberId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditApplication> findByStatus(ApplicationStatus status) {
        log.debug("Finding credit applications by status: {}", status);
        return repository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting credit application by id: {}", id);
        repository.deleteById(id);
    }
}
