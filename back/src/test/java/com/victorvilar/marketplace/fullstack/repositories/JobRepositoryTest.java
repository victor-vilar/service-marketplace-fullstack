package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.*;
import com.victorvilar.marketplace.fullstack.enums.OrderStatus;
import com.victorvilar.marketplace.fullstack.enums.PaymentMethod;
import com.victorvilar.marketplace.fullstack.enums.PaymentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Profile("test")
class JobRepositoryTest {

    @Autowired
    private JobRepository repository;

    @Autowired
    private TestEntityManager manager;

    User user1;
    User user2;
    User user3;

    Job job1;
    Job job2;
    Job job3;

    Order order1;
    Order order2;

    @BeforeEach
    public void SetUp(){
        setUsers();
        setJobs();
        setOrders();

    }


    public void setUsers(){
        user1 = new User();
        user2 = new User();
        user3 = new User();



        user1.setEnabled(true);
        user1.setName("Lorem");
        user1.setPassword("sdff");
        user1.setEmail("lorem1@gmail.com");

        user2.setEnabled(true);
        user2.setName("Ipsum");
        user2.setPassword("sdff");
        user2.setEmail("ipsum@gmail.com");

        user3.setEnabled(true);
        user3.setName("LorIp");
        user3.setPassword("sdff");
        user3.setEmail("lorem3@gmail.com");
    }

    public void setJobs(){

        job1 = new Job();
        job2 = new Job();
        job3 = new Job();



        job1.setTitle("Detetização");
        job2.setTitle("Lavagem Automotiva");
        job3.setTitle("Terceiro job");

        job1.setDescription("detetização de pragas e vetores");
        job2.setDescription("lavagem de carros");
        job3.setDescription("terceiro job");

        job1.setPrice(BigDecimal.valueOf(100));
        job2.setPrice(BigDecimal.valueOf(100));
        job3.setPrice(BigDecimal.valueOf(100));

    }

    void setOrders(){
        order1 = new Order();
        order2 = new Order();



        order1.setOrderStatus(OrderStatus.ABERTA);
        order2.setOrderStatus(OrderStatus.CONCLUIDO);

        order1.setTotalAmount(BigDecimal.valueOf(1000));
        order2.setTotalAmount(BigDecimal.valueOf(1000));

        Payment p1 = new Payment();
        Payment p2 = new Payment();

        p1.setPaymentStatus(PaymentStatus.ABERTA);
        p2.setPaymentStatus(PaymentStatus.CONCLUIDO);

        p1.setPaymentMethod(PaymentMethod.BOLETO);
        p2.setPaymentMethod(PaymentMethod.PIX);

        order1.setPayment(p1);
        order2.setPayment(p2);



    }

    private void configureBeforeTest(){

    }

    @Test
    void findByIdWithOrdersDeveTrazerJobsComSuasOrders() {

        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);
        job2.setCategory(cat2);


        user1 = manager.persist(user1);
        user2 = manager.persist(user2);
        user3 = manager.persist(user3);

        job1.setProvider(user1);
        job2.setProvider(user2);

        job1 = manager.persist(job1);
        job2 = manager.persist(job2);

        order1.setJob(job1);
        order2.setJob(job2);

        order1.setCustomer(user3);
        order2.setCustomer(user3);

        manager.persist(order1);
        manager.persist(order2);

        Optional<Job> joba1 = repository.findByIdWithOrders(job1.getId());
        Optional<Job> joba2 = repository.findByIdWithOrders(job2.getId());

        assertFalse(joba1.isEmpty());
        assertFalse(joba2.isEmpty());

        Assertions.assertEquals(job1.getId(),joba1.get().getId());
        Assertions.assertEquals(job2.getId(),joba2.get().getId());
        assertTrue(joba1.get().getOrders().size() > 0);
        assertTrue(joba2.get().getOrders().size() > 0);

    }

    @Test
    void findByProviderIdDeveTrazerOsJobsPeloIdDoProvider() {

        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);
        job2.setCategory(cat2);

        user1 = manager.persist(user1);
        user2 = manager.persist(user2);
        user3 = manager.persist(user3);

        job1.setProvider(user1);
        job2.setProvider(user1);

        job1 = manager.persist(job1);
        job2 = manager.persist(job2);

        List<Job> jobs = repository.findByProviderId(user1.getId());
        assertTrue(jobs.size() == 2);
        assertEquals(jobs.get(0).getId(),job1.getId());
        assertEquals(jobs.get(1).getId(),job2.getId());
        assertEquals(jobs.get(0).getProvider().getId(), user1.getId());
        assertEquals(jobs.get(1).getProvider().getId(), user1.getId());
    }

    @Test
    void findByCategoryName() {

        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);
        job2.setCategory(cat2);
        user1 = manager.persist(user1);

        job1.setProvider(user1);
        job2.setProvider(user1);

        job1 = manager.persist(job1);
        job2 = manager.persist(job2);

        List<Job> jobComCategoria1 = repository.findByCategoryName(cat1.getName());
        List<Job> jobComCategoria2 = repository.findByCategoryName(cat2.getName());

        assertEquals(jobComCategoria1.get(0).getCategory(), cat1);
        assertEquals(jobComCategoria2.get(0).getCategory(), cat2);


    }

    @Test
    void findByActiveDeveSomenteTrazerOsJobsQUeTiveremOMesmoBoolean(){
        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);
        job2.setCategory(cat2);
        job3.setCategory(cat2);
        user1 = manager.persist(user1);

        job1.setProvider(user1);
        job2.setProvider(user1);
        job3.setProvider(user1);

        job1 = manager.persist(job1);
        job2 = manager.persist(job2);
        job3.setActive(false);
        job3 = manager.persist(job3);



        List<Job> activeJobs = repository.findByActive(true);
        assertEquals(activeJobs.size(),2);
        assertTrue(activeJobs.get(0).isActive());
        assertTrue(activeJobs.get(1).isActive());

        List<Job> unActiveJobs = repository.findByActive(false);
        assertEquals(unActiveJobs.size(),1);
        assertFalse(unActiveJobs.get(0).isActive());





    }
}