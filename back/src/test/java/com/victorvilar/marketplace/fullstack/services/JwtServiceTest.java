package com.victorvilar.marketplace.fullstack.services;

import com.victorvilar.marketplace.fullstack.domain.JwtClaims;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService service;

    @BeforeEach
    void setUp(){
      service = new JwtService();
        ReflectionTestUtils.setField(service,"secretKey","chave_padra_para_desenvolvimento_local_com_mais_de_32_caracteres");
        ReflectionTestUtils.setField(service,"tokenIssuer","service-marketplace-fullstack");
    }

    @Test
    void deveMontarUmUserComOsDadosDoJWTComSucesso(){
        User user = new User();
        user.setName("Victor");
        user.setPhoneNumber("123456");
        user.addRole(TipoUsuario.ADMINISTRADOR);

        Map<String, String> claims = new HashMap<>();
        claims.put(JwtClaims.AUTHORITIES, user.getAuthorities().toString());
        claims.put(JwtClaims.NAME,user.getName());
        claims.put(JwtClaims.PHONE,user.getPhoneNumber());

        String jwt = service.generateKey(user.getName(),"dados",claims);
        User user2 = service.verifyKey(jwt);

        Assertions.assertEquals(user.getName(),user2.getName());
        Assertions.assertEquals(user.getPhoneNumber(),user2.getPhoneNumber());
        Assertions.assertArrayEquals(user.getAuthorities().toArray(),user2.getAuthorities().toArray());
    }

}