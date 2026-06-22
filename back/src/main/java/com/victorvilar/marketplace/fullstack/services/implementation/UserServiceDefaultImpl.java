package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.dtos.RegisterDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import com.victorvilar.marketplace.fullstack.exceptions.UserNotFoundException;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.mappers.UserMapper;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceDefaultImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final String USER_NOT_FOUND = "Usuário não encontrado !";
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceDefaultImpl(UserRepository repository,UserMapper mapper, PasswordEncoder encoder){
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;

    }

    @Override
    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(u -> mapper.toDto(u)).toList();
    }

    @Override
    public UserDTO getById(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> {throw new UserNotFoundException(USER_NOT_FOUND);});
        return mapper.toDto(user);
    }

    private User getByIdNoMap(UUID id){
        return repository.findById(id).orElseThrow(() -> {throw new UserNotFoundException(USER_NOT_FOUND);});
    }

    @Override
    public UserDTO save(UserDTO entity) {

        if(entity.getId() !=null){
            return update(entity);
        }

        User user = mapper.toEntity(entity);
        return mapper.toDto(repository.save(user));

    }

    @Override
    public UserDTO save(RegisterDTO register){

        User user = new User();
        user.setName(register.name());
        user.setEmail(register.email());
        user.setPassword(encoder.encode(register.password()));

        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDTO update(UserDTO entity) {
        User user = getByIdNoMap(UUID.fromString(entity.getId()));
        mapper.copyData(entity,user);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public void delete(UUID id) {
        User user = getByIdNoMap(id);
        user.setEnabled(false);
        repository.save(user);
    }

    @Override
    public List<UserDTO> getAllActive() {
        return repository.findAllActive().stream().map(u -> mapper.toDto(u)).toList();
    }

    @Override
    public UserDTO getByIdWithJobs(UUID id) {
        Optional<User> user = repository.findByIdWithJobs(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return mapper.toDto(user.get());
    }

    @Override
    public UserDTO getByIdWithOrders(UUID id) {
        Optional<User> user = repository.findByIdWithOrders(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return mapper.toDto(user.get());
    }

    @Override
    public UserDTO addRole(UUID id , TipoUsuario tipo) {
        User user = getByIdNoMap(id);
        user.addRole(tipo);
        return mapper.toDto(user);
    }

    @Override
    public UserDTO removeRole(UUID id ,TipoUsuario tipo){
        User user = getByIdNoMap(id);
        user.addRole(tipo);
        return mapper.toDto(user);
    }
}
