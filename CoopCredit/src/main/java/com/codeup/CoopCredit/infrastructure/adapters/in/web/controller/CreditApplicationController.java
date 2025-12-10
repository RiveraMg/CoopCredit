package com.codeup.CoopCredit.infrastructure.adapters.in.web.controller;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.domain.ports.in.application.*;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.CreateCreditApplicationDTO;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.mapper.CreditApplicationWebMapper;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.response.AppResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Slf4j
public class CreditApplicationController {

    private final ICreateCreditApplicationUseCase createCreditApplicationUseCase;
    private final IGetCreditApplicationByIdUseCase getCreditApplicationByIdUseCase;
    private final IGetApplicationsByMemberUseCase getApplicationsByMemberUseCase;
    private final IEvaluateCreditApplicationUseCase evaluateCreditApplicationUseCase;
    private final IGetAllPendingApplicationsUseCase getAllPendingApplicationsUseCase;
    private final CreditApplicationWebMapper creditApplicationWebMapper;

    @PostMapping
    @PreAuthorize("hasRole('AFILIADO')")
    public ResponseEntity<AppResponse<Map<String, Object>>> createApplication(
            @Valid @RequestBody CreateCreditApplicationDTO createDTO) {
        log.info("Creating credit application for member: {}", createDTO.getMemberId());
        
        CreditApplication application = creditApplicationWebMapper.toDomain(createDTO);
        CreditApplication savedApplication = createCreditApplicationUseCase.execute(application);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AppResponse.ok(creditApplicationWebMapper.toResponse(savedApplication)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    public ResponseEntity<AppResponse<Map<String, Object>>> getApplicationById(@PathVariable Long id) {
        log.info("Getting application with id: {}", id);
        
        CreditApplication application = getCreditApplicationByIdUseCase.execute(id);
        return ResponseEntity.ok(AppResponse.ok(creditApplicationWebMapper.toResponse(application)));
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    public ResponseEntity<AppResponse<List<Map<String, Object>>>> getApplicationsByMember(
            @PathVariable Long memberId) {
        log.info("Getting applications for member: {}", memberId);
        
        List<CreditApplication> applications = getApplicationsByMemberUseCase.execute(memberId);
        List<Map<String, Object>> responseData = applications.stream()
                .map(creditApplicationWebMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(AppResponse.ok(responseData));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ANALISTA')")
    public ResponseEntity<AppResponse<List<Map<String, Object>>>> getPendingApplications() {
        log.info("Getting all pending applications");
        
        List<CreditApplication> pendingApplications = getAllPendingApplicationsUseCase.execute();
        List<Map<String, Object>> responseData = pendingApplications.stream()
                .map(creditApplicationWebMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(AppResponse.ok(responseData));
    }

    @PostMapping("/{id}/evaluate")
    @PreAuthorize("hasRole('ANALISTA')")
    public ResponseEntity<AppResponse<Map<String, Object>>> evaluateApplication(@PathVariable Long id) {
        log.info("Evaluating application with id: {}", id);
        
        evaluateCreditApplicationUseCase.execute(id);
        CreditApplication application = getCreditApplicationByIdUseCase.execute(id);
        
        return ResponseEntity.ok(AppResponse.ok(creditApplicationWebMapper.toResponse(application)));
    }
}
