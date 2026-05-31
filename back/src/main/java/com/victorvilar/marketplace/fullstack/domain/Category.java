package com.victorvilar.marketplace.fullstack.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="tb_categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
