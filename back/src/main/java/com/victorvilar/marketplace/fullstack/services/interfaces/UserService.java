package com.victorvilar.marketplace.fullstack.services.interfaces;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService{

    public List<UserDTO> getAll();
    public UserDTO getById(UUID id);
    public UserDTO save(UserDTO entity);
    public UserDTO update(UserDTO entity);
    public void delete(UUID id);
    public List<UserDTO> getAllActive();
    public UserDTO getByIdWithJob(UUID id);
    public UserDTO getByIdWithOrder(UUID id);
}
