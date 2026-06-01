package com.victorvilar.marketplace.fullstack.services.interfaces;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService extends CrudService<User>{

    public List<User> getAllActive();
    public User getByIdWithJob(UUID id);
    public User getByIdWithOrder(UUID id);
}
