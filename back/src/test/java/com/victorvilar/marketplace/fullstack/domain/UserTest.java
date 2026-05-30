package com.victorvilar.marketplace.fullstack.domain;

import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    User user;
    Set<SimpleGrantedAuthority> authorities;

    @BeforeEach
    public void setUp(){

        user = new User();
        authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(TipoUsuario.ADMINISTRADOR.getTipo()));
        authorities.add(new SimpleGrantedAuthority(TipoUsuario.ADMINISTRADOR.getTipo()));
        authorities.add(new SimpleGrantedAuthority(TipoUsuario.USUARIO.getTipo()));
    }

    @Test
    void listaStringDoUsuarioDeveRetornarUmaListaDeAlgumaCoisaQueExtendeDeCollection() {
        user.setAuthorities(authorities);
        List<? extends GrantedAuthority> authorities1 = (List<? extends GrantedAuthority>) user.getAuthorities();
        assertEquals(authorities1.get(0).toString(),TipoUsuario.ADMINISTRADOR.getTipo());
        assertEquals(authorities1.get(1).toString(),TipoUsuario.USUARIO.getTipo());
    }
}