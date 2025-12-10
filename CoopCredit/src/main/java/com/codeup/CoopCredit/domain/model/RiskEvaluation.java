package com.codeup.CoopCredit.domain.model;

import com.codeup.CoopCredit.domain.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluation {

    private Long id;
    private Long applicationId;
    private Integer score;
    private RiskLevel riskLevel;
    private BigDecimal debtToIncomeRatio;
    private Boolean meetsSeniorityRequirement;
    private Boolean meetsMaximumAmount;
    private Boolean approved;
    private String rejectionReason;
    private String details;
    private LocalDateTime evaluationDate;
    private LocalDateTime createdAt;

    /**
     * Business rule: Determine risk level based on score
     */
    public static RiskLevel determineRiskLevel(Integer score) {
        if (score == null) {
            return RiskLevel.HIGH;
        }

        if (score >= 701 && score <= 950) {
            return RiskLevel.LOW;
        } else if (score >= 501 && score <= 700) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.HIGH;
        }
    }

    /**
     * Business rule: Check if evaluation is approved
     */
    public boolean isApproved() {
        return Boolean.TRUE.equals(this.approved);
    }

    /**
     * Business rule: Check if all requirements are met
     */
    public boolean meetsAllRequirements() {
        return Boolean.TRUE.equals(meetsSeniorityRequirement) &&
                Boolean.TRUE.equals(meetsMaximumAmount) &&
                debtToIncomeRatio != null &&
                debtToIncomeRatio.compareTo(new BigDecimal("40")) <= 0; // Max 40% debt-to-income
    }

    /**
     * Business rule: Build rejection reason
     */
    public String buildRejectionReason() {
        StringBuilder reason = new StringBuilder();

        if (Boolean.FALSE.equals(meetsSeniorityRequirement)) {
            reason.append("Member does not meet minimum seniority requirement (6 months). ");
        }

        if (Boolean.FALSE.equals(meetsMaximumAmount)) {
            reason.append("Requested amount exceeds maximum allowed (4x salary). ");
        }

        if (debtToIncomeRatio != null && debtToIncomeRatio.compareTo(new BigDecimal("40")) > 0) {
            reason.append("Debt-to-income ratio exceeds 40%. ");
        }

        if (riskLevel == RiskLevel.HIGH) {
            reason.append("Credit score indicates high risk. ");
        }

        return reason.length() > 0 ? reason.toString().trim() : "Does not meet credit policies.";
    }
}
