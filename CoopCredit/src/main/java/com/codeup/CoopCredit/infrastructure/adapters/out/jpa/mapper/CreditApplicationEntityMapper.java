package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper;

import com.codeup.CoopCredit.domain.model.CreditApplication;
import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.CreditApplicationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditApplicationEntityMapper {

    CreditApplication toDomain(CreditApplicationEntity entity);

    CreditApplicationEntity toEntity(CreditApplication domain);
}
