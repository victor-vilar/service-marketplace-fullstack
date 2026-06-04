package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Profile("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private TestEntityManager manager;

    Category cat1;
    Category cat2;
    Category cat3;
    Category cat4;
    Category cat5;


    @BeforeEach
    public void setUp() {

        cat1 = new Category();
        cat2 = new Category();
        cat3 = new Category();
        cat4 = new Category();
        cat5 = new Category();

        cat1.setName("MANUTENÇÃO ELETRICA");
        cat2.setName("PEDREIRO");
        cat3.setName("MARCENARIA");
        cat4.setName("MECANICO");
        cat5.setName("ROTEIRISTA");

        manager.persist(cat1);
        manager.persist(cat2);
        manager.persist(cat3);
        manager.persist(cat4);
        manager.persist(cat5);

    }

    @Test
    void deveEncontrarAsCategoriasDeAcordoComONome() {

        Optional<Category> category = repository.findByName(cat1.getName());
        Assertions.assertNotNull(category);
        Assertions.assertEquals(category.get().getName(), cat1.getName());

        category = repository.findByName(cat2.getName());
        Assertions.assertNotNull(category);
        Assertions.assertEquals(category.get().getName(), cat2.getName());

        category = repository.findByName(cat3.getName());
        Assertions.assertNotNull(category);
        Assertions.assertEquals(category.get().getName(), cat3.getName());

        category = repository.findByName(cat4.getName());
        Assertions.assertNotNull(category);
        Assertions.assertEquals(category.get().getName(), cat4.getName());

        category = repository.findByName(cat5.getName());
        Assertions.assertNotNull(category);
        Assertions.assertEquals(category.get().getName(), cat5.getName());


    }
}