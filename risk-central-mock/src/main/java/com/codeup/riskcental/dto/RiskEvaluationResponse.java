package com.codeup.riskcental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationResponse {
    private String document;
    private Integer score;
    private String riskLevel;
    private String details;
}
