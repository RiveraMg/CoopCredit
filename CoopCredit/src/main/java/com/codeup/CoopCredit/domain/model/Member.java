package com.codeup.CoopCredit.domain.model;

import com.codeup.CoopCredit.domain.model.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long id;
    private String document;
    private String name;
    private BigDecimal salary;
    private LocalDate membershipDate;
    private MemberStatus status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business rule: Check if member is active
     */
    public boolean isActive() {
        return MemberStatus.ACTIVE.equals(this.status);
    }

    /**
     * Business rule: Check if member meets minimum seniority (6 months)
     */
    public boolean meetsMinimumSeniority() {
        if (membershipDate == null) {
            return false;
        }
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return membershipDate.isBefore(sixMonthsAgo) || membershipDate.isEqual(sixMonthsAgo);
    }

    /**
     * Business rule: Calculate maximum loan amount (4x salary)
     */
    public BigDecimal getMaximumLoanAmount() {
        if (salary == null) {
            return BigDecimal.ZERO;
        }
        return salary.multiply(new BigDecimal("4"));
    }

    /**
     * Business rule: Validate if member can request a specific amount
     */
    public boolean canRequestAmount(BigDecimal requestedAmount) {
        if (requestedAmount == null || requestedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return requestedAmount.compareTo(getMaximumLoanAmount()) <= 0;
    }
}