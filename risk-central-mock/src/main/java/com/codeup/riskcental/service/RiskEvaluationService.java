package com.codeup.riskcental.service;

import com.codeup.riskcental.dto.RiskEvaluationRequest;
import com.codeup.riskcental.dto.RiskEvaluationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RiskEvaluationService {

    /**
     * Evaluates credit risk based on document, amount, and term months
     * The score is deterministic based on the document hash
     * Same document always returns same score
     */
    public RiskEvaluationResponse evaluate(RiskEvaluationRequest request) {
        log.info("Evaluating risk for document: {}", request.getDocument());

        // Generate deterministic score based on document
        int score = generateScore(request.getDocument());
        String riskLevel = classifyRisk(score);
        String details = generateDetails(riskLevel);

        log.info("Risk evaluation result - Document: {}, Score: {}, Level: {}", 
                request.getDocument(), score, riskLevel);

        return RiskEvaluationResponse.builder()
                .document(request.getDocument())
                .score(score)
                .riskLevel(riskLevel)
                .details(details)
                .build();
    }

    /**
     * Generate deterministic score between 300-950
     * Uses document hash to ensure same document always gets same score
     */
    private int generateScore(String document) {
        // Use hash code of document as seed
        int seed = Math.abs(document.hashCode() % 1000);
        
        // Scale seed to 300-950 range
        // Formula: min + (seed % range)
        int min = 300;
        int max = 950;
        int range = max - min + 1;
        
        return min + (seed % range);
    }

    /**
     * Classify risk level based on score
     * 300-500: HIGH RISK
     * 501-700: MEDIUM RISK
     * 701-950: LOW RISK
     */
    private String classifyRisk(int score) {
        if (score <= 500) {
            return "HIGH";
        } else if (score <= 700) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    /**
     * Generate description based on risk level
     */
    private String generateDetails(String riskLevel) {
        return switch (riskLevel) {
            case "HIGH" -> "High credit risk. Limited credit history or previous defaults detected.";
            case "MEDIUM" -> "Moderate credit risk. Some payment history issues or moderate debt levels.";
            case "LOW" -> "Low credit risk. Good payment history and manageable debt levels.";
            default -> "Unknown risk level";
        };
    }
}
