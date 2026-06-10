package com.victorvilar.marketplace.fullstack.configuration;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppDefaultUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    private static final String USER_NOT_FOUND = "Usuário não encontrado !";

    @Autowired
    public AppDefaultUserDetailsServiceImpl(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return user;
    }
}
