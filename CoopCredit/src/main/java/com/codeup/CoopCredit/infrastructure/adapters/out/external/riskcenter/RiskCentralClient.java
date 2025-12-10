package com.codeup.CoopCredit.infrastructure.adapters.out.external.riskcenter;

import com.codeup.CoopCredit.domain.exception.RiskEvaluationException;
import com.codeup.CoopCredit.domain.ports.out.IRiskCentralPort;
import com.codeup.CoopCredit.infrastructure.adapters.out.external.riskcenter.dto.RiskEvaluationRequestDTO;
import com.codeup.CoopCredit.infrastructure.adapters.out.external.riskcenter.dto.RiskEvaluationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Client for communicating with the external Risk Central service
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RiskCentralClient implements IRiskCentralPort {

    private final RestTemplate restTemplate;

    @Value("${risk-central.url:http://localhost:8081}")
    private String riskCentralUrl;

    @Override
    public RiskCentralResponse evaluate(String document, BigDecimal amount, Integer termMonths) {
        log.info("Calling Risk Central for document: {}", document);

        try {
            String url = riskCentralUrl + "/risk-evaluation";

            RiskEvaluationRequestDTO request = RiskEvaluationRequestDTO.builder()
                    .document(document)
                    .amount(amount)
                    .termMonths(termMonths)
                    .build();

            RiskEvaluationResponseDTO response = restTemplate.postForObject(
                    url,
                    request,
                    RiskEvaluationResponseDTO.class
            );

            if (response == null) {
                throw new RiskEvaluationException("Risk Central returned null response");
            }

            log.info("Risk Central response - Score: {}, Level: {}", response.getScore(), response.getRiskLevel());

            return new RiskCentralResponse(
                    response.getDocument(),
                    response.getScore(),
                    response.getRiskLevel(),
                    response.getDetails()
            );
        } catch (RiskEvaluationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error calling Risk Central: {}", e.getMessage(), e);
            throw new RiskEvaluationException("Failed to evaluate credit risk: " + e.getMessage());
        }
    }
}