package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.isEnabled = true")
    public List<User> findAllActive();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.jobs WHERE u.id = :id")
    public User findByIdWithJobs(@Param("id") UUID id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :id")
    public User findByIdWithOrders(@Param("id") UUID id);

    public Optional<User> findByEmail(String email);
}

