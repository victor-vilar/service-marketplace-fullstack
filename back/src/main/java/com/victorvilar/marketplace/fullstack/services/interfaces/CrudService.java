package com.victorvilar.marketplace.fullstack.services.interfaces;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {

    public List<T> getAll();
    public T getById(UUID id);
    public T save(T entity);
    public T update(T entity);
    public void delete(UUID id);


}
