package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * interface temporaria pois preciso adicionar o modelMapper na, mas a filha da puta da tim
 * me deixou sem internet por isso nao vou conseguir adiciona-lo agora.
 */
@Service
public class UserMapper {

    public final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User toEntity(UserDTO dto) {
        return mapper.map(dto, User.class);
    }

    public UserDTO toDto(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public void copyData(UserDTO dto, User user) {
        mapper.map(dto, user);
    }
}

