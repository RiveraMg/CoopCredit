package com.codeup.CoopCredit.domain.model;

import com.codeup.CoopCredit.domain.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication {

    private Long id;
    private Long memberId;
    private BigDecimal requestedAmount;
    private Integer termMonths;
    private BigDecimal proposedRate;
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private RiskEvaluation riskEvaluation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business rule: Calculate monthly payment using simple interest
     * Formula: (Principal * (1 + Rate * Time)) / Months
     */
    public BigDecimal calculateMonthlyPayment() {
        if (requestedAmount == null || termMonths == null || proposedRate == null) {
            return BigDecimal.ZERO;
        }

        // Convert annual rate to monthly and decimal (e.g., 12% = 0.12)
        BigDecimal monthlyRate = proposedRate.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP)
                .divide(new BigDecimal("12"), 6, RoundingMode.HALF_UP);

        // Total interest
        BigDecimal totalInterest = requestedAmount.multiply(monthlyRate).multiply(new BigDecimal(termMonths));

        // Total amount to pay
        BigDecimal totalAmount = requestedAmount.add(totalInterest);

        // Monthly payment
        return totalAmount.divide(new BigDecimal(termMonths), 2, RoundingMode.HALF_UP);
    }

    /**
     * Business rule: Calculate debt-to-income ratio
     */
    public BigDecimal calculateDebtToIncomeRatio(BigDecimal memberSalary) {
        if (memberSalary == null || memberSalary.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyPayment = calculateMonthlyPayment();
        return monthlyPayment.divide(memberSalary, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * Business rule: Check if application is pending
     */
    public boolean isPending() {
        return ApplicationStatus.PENDING.equals(this.status);
    }

    /**
     * Business rule: Approve application
     */
    public void approve() {
        this.status = ApplicationStatus.APPROVED;
    }

    /**
     * Business rule: Reject application
     */
    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }
}