package com.victorvilar.marketplace.fullstack.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.UUID;



@ExtendWith(MockitoExtension.class)
public class UserJobOrderTest {

    User user1;
    User user2;

    Job job1;
    Job job2;

    Order order1;
    Order order2;

    @BeforeEach
    public void setUp(){

        setUsers();
        setJobs();

    }

    @Test
    public void seterProviderDeJobDeveFazerComQueOJobEntreNaListaDeJobsDoUser(){
        Assertions.assertFalse(user1.getJobs().contains(job1));
        job1.setProvider(user1);
        Assertions.assertTrue(user1.getJobs().size() == 1);
        Assertions.assertTrue(user1.getJobs().contains(job1));

        Assertions.assertFalse(user1.getJobs().contains(job2));
        job2.setProvider(user1);
        Assertions.assertTrue(user1.getJobs().size() == 2);
        Assertions.assertTrue(user1.getJobs().contains(job2));

        Assertions.assertTrue(job1.getProvider().equals(user1));
        Assertions.assertTrue(job2.getProvider().equals(user1));
    }

    @Test
    public void seterCustomerDeveAdicionarAOrderNaListaDoCustomer(){
        order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setCustomer(user1);
        Assertions.assertEquals(user1.getOrders().size() ,1);


        order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setCustomer(user1);
        Assertions.assertEquals(user1.getOrders().size(), 2);


        Assertions.assertTrue(user1.getOrders().contains(order1));
        Assertions.assertTrue(user1.getOrders().contains(order2));
    }

    @Test
    public void seterJobDeveAdicionarOrderNaListaDoJob(){
        order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setJob(job1);
        Assertions.assertEquals(job1.getOrders().size() ,1);


        order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setJob(job1);
        Assertions.assertEquals(job1.getOrders().size(), 2);

        Assertions.assertTrue(job1.getOrders().contains(order1));
        Assertions.assertTrue(job1.getOrders().contains(order2));
    }

    @Test
    public void MetodoNaoDeveColocarCustomerSeOCustomerForOMesmoQueOfereceOServiço(){
        order1 = new Order();
        order1.setId(UUID.randomUUID());
        job1.setProvider(user1);

        //adiciona job sem problema enquanto o customer for nulo
        order1.setJob(job1);
        Assertions.assertEquals(order1.getJob(), job1);

        //não adiciona pois o serviço selecionado, é feito pelo proprio customer
        order1.setCustomer(user1);
        Assertions.assertTrue(order1.getCustomer()  == null);
    }

    @Test
    public void MetodoNaoDeveColocarJobSeOJobForOferecidoPeloCustomerSelecionado(){
        order1 = new Order();
        order1.setId(UUID.randomUUID());
        job1.setProvider(user1);

        //adiciona customer
        order1.setCustomer(user1);
        Assertions.assertEquals(order1.getCustomer(), user1);

        order1.setJob(job1);
        Assertions.assertTrue(order1.getJob()  == null);


    }

    @Test
    public void objetoDevePossuirJobECustomerDesdeQueOJobNaoSejaRealizadoPeloCustomerSelecionado(){
        order1 = new Order();
        order1.setId(UUID.randomUUID());
        job1.setProvider(user1);

        order1.setJob(job1);
        order1.setCustomer(user2);

        Assertions.assertNotNull(order1.getJob());
        Assertions.assertNotNull(order1.getCustomer());

        Assertions.assertEquals(order1.getJob(), job1);
        Assertions.assertEquals(order1.getCustomer(), user2);

        Assertions.assertTrue(job1.getOrders().contains(order1));
        Assertions.assertTrue(user2.getOrders().contains(order1));
    }


    public void setUsers(){
        user1 = new User();
        user2 = new User();

        user1.setId(UUID.randomUUID());
        user2.setId(UUID.randomUUID());

        user1.setName("Lorem");
        user2.setName("Ipsum");

        user1.setEmail("lorem@gmail.com");
        user2.setEmail("ipsum@gmail.com");

        user1.setPhoneNumber("1234");
        user2.setPhoneNumber("1234");

    }


    public void setJobs(){

        job1 = new Job();
        job2 = new Job();

        job1.setId(UUID.randomUUID());
        job2.setId(UUID.randomUUID());

        job1.setTitle("Detetização");
        job2.setTitle("Lavagem Automotiva");

        job1.setDescription("detetização de pragas e vetores");
        job2.setDescription("lavagem de carros");

        job1.setPrice(BigDecimal.valueOf(100));
        job2.setPrice(BigDecimal.valueOf(100));

    }




}

