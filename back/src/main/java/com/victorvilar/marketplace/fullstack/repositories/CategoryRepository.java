package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String name);
}
