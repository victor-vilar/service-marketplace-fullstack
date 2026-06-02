package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    void copyData(UserDTO dto, @MappingTarget User entity);

    User toEntity(UserDTO dto);

    UserDTO toDto(User entity);

}
