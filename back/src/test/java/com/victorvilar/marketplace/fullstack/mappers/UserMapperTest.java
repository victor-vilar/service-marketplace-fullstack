package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import com.victorvilar.marketplace.fullstack.mappers.UserMapper;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    UserDTO dto;
    User user;

    @Test
    void deveCopiarOsDadosDoDtoParaAEntity(){

        dto = new UserDTO(UUID.randomUUID(),"Astolfo","astolfo@gmail.com","123456789");
        user = new User();
        user.setName("Ipsum");
        user.setEmail("ipsum@hotmail.com");
        user.setPhoneNumber("987654321");

        mapper.copyData(dto,user);

        Assertions.assertEquals(user.getName(),dto.name());
        Assertions.assertEquals(user.getEmail(),dto.email());
        Assertions.assertEquals(user.getPhoneNumber(),dto.phoneNumber());
    }

    @Test
    void deveCriarUmNovoDTOComAEntiduadePassada(){
        user = new User();
        user.setName("Ipsum");
        user.setEmail("ipsum@hotmail.com");
        user.setPhoneNumber("987654321");

        dto = mapper.toDto(user);
        Assertions.assertEquals(user.getId(),dto.id());
        Assertions.assertEquals(user.getName(),dto.name());
        Assertions.assertEquals(user.getEmail(),dto.email());
        Assertions.assertEquals(user.getPhoneNumber(),dto.phoneNumber());
    }

    @Test
    void deveCriarUmNovoUserCopiandoDto(){

        dto = new UserDTO(UUID.randomUUID(),"Astolfo","astolfo@gmail.com","123456789");
        user = mapper.toEntity(dto);
        
        Assertions.assertEquals(user.getId(),dto.id());
        Assertions.assertEquals(user.getName(),dto.name());
        Assertions.assertEquals(user.getEmail(),dto.email());
        Assertions.assertEquals(user.getPhoneNumber(),dto.phoneNumber());

    }



}