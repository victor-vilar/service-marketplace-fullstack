package com.victorvilar.marketplace.fullstack.services.interfaces;

import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;

import java.util.List;
import java.util.UUID;

public interface UserService{

    public List<UserDTO> getAll();
    public UserDTO getById(UUID id);
    public UserDTO save(UserDTO entity);
    public UserDTO update(UserDTO entity);
    public void delete(UUID id);
    public List<UserDTO> getAllActive();
    public UserDTO getByIdWithJobs(UUID id);
    public UserDTO getByIdWithOrders(UUID id);
    public UserDTO addRole(UUID id , TipoUsuario tipo);
    public UserDTO removeRole(UUID id ,TipoUsuario tipo);
}
