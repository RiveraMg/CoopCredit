package com.codeup.riskcental.controller;

import com.codeup.riskcental.dto.RiskEvaluationRequest;
import com.codeup.riskcental.dto.RiskEvaluationResponse;
import com.codeup.riskcental.service.RiskEvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk-evaluation")
@RequiredArgsConstructor
@Slf4j
public class RiskEvaluationController {

    private final RiskEvaluationService riskEvaluationService;

    /**
     * Evaluate credit risk
     * POST /risk-evaluation
     * 
     * Request:
     * {
     *   "document": "1017654311",
     *   "amount": 5000000,
     *   "termMonths": 36
     * }
     * 
     * Response:
     * {
     *   "document": "1017654311",
     *   "score": 642,
     *   "riskLevel": "MEDIUM",
     *   "details": "Moderate credit risk..."
     * }
     */
    @PostMapping
    public ResponseEntity<RiskEvaluationResponse> evaluateRisk(
            @RequestBody RiskEvaluationRequest request) {
        log.info("Received risk evaluation request for document: {}", request.getDocument());

        if (request.getDocument() == null || request.getDocument().isEmpty()) {
            log.warn("Document is required");
            return ResponseEntity.badRequest().build();
        }

        RiskEvaluationResponse response = riskEvaluationService.evaluate(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Risk Central Mock Service is running");
    }
}
