package com.codeup.CoopCredit.infrastructure.adapters.out.external.riskcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationRequestDTO {
    private String document;
    private BigDecimal amount;
    private Integer termMonths;
}
