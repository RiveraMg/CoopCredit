package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper;

import com.codeup.CoopCredit.domain.model.RiskEvaluation;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.RiskEvaluationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RiskEvaluationEntityMapper {

    RiskEvaluation toDomain(RiskEvaluationEntity entity);

    RiskEvaluationEntity toEntity(RiskEvaluation domain);
}
