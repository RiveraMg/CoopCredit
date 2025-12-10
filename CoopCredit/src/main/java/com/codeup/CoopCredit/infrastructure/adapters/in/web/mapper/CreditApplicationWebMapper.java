package com.codeup.CoopCredit.infrastructure.adapters.in.web.mapper;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.model.RiskEvaluation;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.CreateCreditApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CreditApplicationWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    @Mapping(target = "status", expression = "java(com.codeup.CoopCredit.domain.model.enums.ApplicationStatus.PENDING)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "riskEvaluation", ignore = true)
    CreditApplication toDomain(CreateCreditApplicationDTO dto);

    default Map<String, Object> toResponse(CreditApplication application) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", application.getId());
        response.put("memberId", application.getMemberId());
        response.put("requestedAmount", application.getRequestedAmount());
        response.put("termMonths", application.getTermMonths());
        response.put("proposedRate", application.getProposedRate());
        response.put("applicationDate", application.getApplicationDate());
        response.put("status", application.getStatus().name());
        response.put("createdAt", application.getCreatedAt());
        response.put("updatedAt", application.getUpdatedAt());
        
        if (application.getRiskEvaluation() != null) {
            response.put("riskEvaluation", toRiskEvaluationMap(application.getRiskEvaluation()));
        }
        
        return response;
    }

    default Map<String, Object> toRiskEvaluationMap(RiskEvaluation evaluation) {
        Map<String, Object> riskMap = new LinkedHashMap<>();
        riskMap.put("id", evaluation.getId());
        riskMap.put("score", evaluation.getScore());
        riskMap.put("riskLevel", evaluation.getRiskLevel().name());
        riskMap.put("details", evaluation.getDetails());
        riskMap.put("evaluationDate", evaluation.getEvaluationDate());
        return riskMap;
    }
}
