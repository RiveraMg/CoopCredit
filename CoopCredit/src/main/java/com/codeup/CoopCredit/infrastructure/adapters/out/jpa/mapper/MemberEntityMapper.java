package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper;


import com.codeup.CoopCredit.domain.model.Member;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.MemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberEntityMapper {

    Member toDomain(MemberEntity entity);

    MemberEntity toEntity(Member domain);
}