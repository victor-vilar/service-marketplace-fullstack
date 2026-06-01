package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.exceptions.UserNotFoundException;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import com.victorvilar.marketplace.fullstack.services.interfaces.CrudService;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceDefaultImplementation implements UserService<User> {

    private final UserRepository repository;
    private final String USER_NOT_FOUND = "Usuário não encontrado !";

    @Autowired
    public UserServiceDefaultImplementation(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> {throw new UserNotFoundException(USER_NOT_FOUND);});
    }

    @Override
    public User save(User entity) {

        if(entity.getId() !=null){
            return update(entity);
        }

        return repository.save(entity);

    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> {throw new UserNotFoundException(USER_NOT_FOUND);});
        user.setEnabled(false);
    }
}
