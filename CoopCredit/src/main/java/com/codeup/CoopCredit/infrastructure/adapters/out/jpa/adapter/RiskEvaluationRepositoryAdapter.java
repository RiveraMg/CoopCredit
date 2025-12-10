package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.adapter;

import com.codeup.CoopCredit.domain.model.RiskEvaluation;
import com.codeup.CoopCredit.domain.ports.out.IRiskEvaluationRepositoryPort;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.RiskEvaluationEntity;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper.RiskEvaluationEntityMapper;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.repository.IRiskEvaluationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RiskEvaluationRepositoryAdapter implements IRiskEvaluationRepositoryPort {

    private final IRiskEvaluationJpaRepository repository;
    private final RiskEvaluationEntityMapper mapper;

    @Override
    public RiskEvaluation save(RiskEvaluation evaluation) {
        log.debug("Saving risk evaluation for application: {}", evaluation.getApplicationId());
        RiskEvaluationEntity entity = mapper.toEntity(evaluation);
        RiskEvaluationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RiskEvaluation> findById(Long id) {
        log.debug("Finding risk evaluation by id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<RiskEvaluation> findByApplicationId(Long applicationId) {
        log.debug("Finding risk evaluation by application id: {}", applicationId);
        return repository.findByApplicationId(applicationId)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting risk evaluation by id: {}", id);
        repository.deleteById(id);
    }
}
