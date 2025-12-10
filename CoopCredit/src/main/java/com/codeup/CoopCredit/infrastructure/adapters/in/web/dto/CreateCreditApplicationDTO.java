package com.codeup.CoopCredit.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditApplicationDTO {

    @NotNull(message = "Member ID cannot be null")
    private Long memberId;

    @NotNull(message = "Requested amount cannot be null")
    @Min(value = 100000, message = "Minimum loan amount is 100,000")
    private BigDecimal requestedAmount;

    @NotNull(message = "Loan term (months) cannot be null")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    private Integer termMonths;

    @NotNull(message = "Proposed interest rate cannot be null")
    @Min(value = 0, message = "Interest rate must be >= 0")
    private BigDecimal proposedRate;
}
