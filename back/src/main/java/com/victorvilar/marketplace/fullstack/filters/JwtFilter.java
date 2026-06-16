package com.victorvilar.marketplace.fullstack.filters;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Autowired
    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = findToken(request);
        if(token != null){
           User subject = jwtService.verifyKey(token);
           if(subject != null){
               Authentication authentication = new UsernamePasswordAuthenticationToken(subject.getName(),null,subject.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * Encontrao token se existir no header da requisição.
     * O token normalmente vem com o prefixo 'Bearer ', que é removido caso o token exista
     * @param request requisição HTTP
     * @return string vazia caso não haja o header 'Authorization', ou o token
     */
    private String findToken(HttpServletRequest request){

        String token = request.getHeader(AUTHORIZATION_HEADER);
        if(token == null || !token.startsWith(AUTHORIZATION_HEADER_PREFIX)){
            return null;
        }
        return token.replace(AUTHORIZATION_HEADER_PREFIX,"");
    }
}



