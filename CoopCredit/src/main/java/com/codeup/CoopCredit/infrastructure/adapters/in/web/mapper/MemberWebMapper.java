package com.codeup.CoopCredit.infrastructure.adapters.in.web.mapper;

import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.domain.model.enums.MemberStatus;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.RegisterMemberDTO;
import com.codeup.CoopCredit.infrastructure.adapters.in.web.dto.UpdateMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.LinkedHashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface MemberWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", expression = "java(com.codeup.CoopCredit.domain.model.enums.MemberStatus.ACTIVE)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Member toDomain(RegisterMemberDTO dto);

    default Map<String, Object> toResponse(Member member) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", member.getId());
        response.put("document", member.getDocument());
        response.put("name", member.getName());
        response.put("salary", member.getSalary());
        response.put("membershipDate", member.getMembershipDate());
        response.put("status", member.getStatus().name());
        response.put("createdAt", member.getCreatedAt());
        response.put("updatedAt", member.getUpdatedAt());
        return response;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "membershipDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDTO(UpdateMemberDTO updateDto, @MappingTarget Member member);
}
