package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.orders WHERE j.id = :id")
    public Optional<Job> findByIdWithOrders(@Param("id") UUID id);
    public List<Job> findByActive(boolean active);
    public List<Job> findByProviderId(UUID providerId);
    public List<Job> findByCategoryName(String category);


}
