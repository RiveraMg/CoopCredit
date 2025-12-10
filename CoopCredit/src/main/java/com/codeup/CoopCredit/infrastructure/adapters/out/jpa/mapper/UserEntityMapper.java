package com.codeup.CoopCredit.infrastructure.adapters.out.jpa.mapper;


import com.codeup.CoopCredit.infrastructure.adapters.out.jpa.entity.UserEntity;
import com.codeup.CoopCredit.domain.ports.out.IUserRepositoryPort.UserDetailsData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "role", expression = "java(entity.getRoles().isEmpty() ? null : entity.getRoles().iterator().next().getName())")
    UserDetailsData toDomain(UserEntity entity);
}
