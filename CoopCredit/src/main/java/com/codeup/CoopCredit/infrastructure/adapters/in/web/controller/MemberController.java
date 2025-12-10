package com.codeup.CoopCredit.infrastructure.adapters.in.web.controller;

import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.ports.in.member.*;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.RegisterMemberDTO;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.UpdateMemberDTO;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.mapper.MemberWebMapper;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.response.AppResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final IRegisterMemberUseCase registerMemberUseCase;
    private final IGetMemberByIdUseCase getMemberByIdUseCase;
    private final IGetMemberByDocumentUseCase getMemberByDocumentUseCase;
    private final IUpdateMemberUseCase updateMemberUseCase;
    private final MemberWebMapper memberWebMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppResponse<Map<String, Object>>> registerMember(@Valid @RequestBody RegisterMemberDTO registerMemberDTO) {
        log.info("Registering new member with document: {}", registerMemberDTO.getDocument());
        
        Member member = memberWebMapper.toDomain(registerMemberDTO);
        Member savedMember = registerMemberUseCase.execute(member);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AppResponse.ok(memberWebMapper.toResponse(savedMember)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    public ResponseEntity<AppResponse<Map<String, Object>>> getMemberById(@PathVariable Long id) {
        log.info("Getting member with id: {}", id);
        
        Member member = getMemberByIdUseCase.execute(id);
        return ResponseEntity.ok(AppResponse.ok(memberWebMapper.toResponse(member)));
    }

    @GetMapping("/document/{document}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    public ResponseEntity<AppResponse<Map<String, Object>>> getMemberByDocument(@PathVariable String document) {
        log.info("Getting member with document: {}", document);
        
        Member member = getMemberByDocumentUseCase.execute(document);
        return ResponseEntity.ok(AppResponse.ok(memberWebMapper.toResponse(member)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ADMIN')")
    public ResponseEntity<AppResponse<Map<String, Object>>> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberDTO updateMemberDTO) {
        log.info("Updating member with id: {}", id);
        
        Member member = getMemberByIdUseCase.execute(id);
        memberWebMapper.updateFromDTO(updateMemberDTO, member);
        Member updatedMember = updateMemberUseCase.execute(id, member);
        
        return ResponseEntity.ok(AppResponse.ok(memberWebMapper.toResponse(updatedMember)));
    }
}
