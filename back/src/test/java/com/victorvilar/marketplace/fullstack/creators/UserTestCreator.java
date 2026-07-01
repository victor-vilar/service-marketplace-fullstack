package com.victorvilar.marketplace.fullstack.creators;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.UUID;

public abstract class UserTestCreator {

    public static User criarUserCompleto(){
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Usuario completo");
        user.setEmail("usuariocompleto@email.com");
        user.setPhoneNumber("123456");
        user.setEnabled(true);
        user.setPassword("445566");
        user.setAuthorities(Set.of(new SimpleGrantedAuthority(TipoUsuario.ADMINISTRADOR.getTipo())));
        return user;
    }

    public static UserDTO criarUserDTOCompleto(){
        return UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .name("Usuario dto Completo")
                .email("usuariodtocompleto@email.com")
                .phoneNumber("123456")
                .password("445566")
                .authorities(Set.of(TipoUsuario.USUARIO.toString(), TipoUsuario.ADMINISTRADOR.toString()))
                .build();
    }
}
