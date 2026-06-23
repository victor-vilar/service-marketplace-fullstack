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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Profile("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager manager;

    User user1;
    User user2;
    User user3;

    Job job1;
    Job job2;

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


        job1.setTitle("Detetização");
        job2.setTitle("Lavagem Automotiva");

        job1.setDescription("detetização de pragas e vetores");
        job2.setDescription("lavagem de carros");

        job1.setPrice(BigDecimal.valueOf(100));
        job2.setPrice(BigDecimal.valueOf(100));

    }

    void setOrders(){
        order1 = new Order();
        order2 = new Order();


        order1.setOrderStatus(OrderStatus.EM_ANDAMENTO);
        order2.setOrderStatus(OrderStatus.CONCLUIDO);

        order1.setTotalAmount(BigDecimal.valueOf(1000));
        order2.setTotalAmount(BigDecimal.valueOf(1000));

        Payment p1 = new Payment();
        Payment p2 = new Payment();

        p1.setStatus(PaymentStatus.EM_ABERTO);
        p2.setStatus(PaymentStatus.PAGAMENTO_REALIZADO);

        p1.setMethod(PaymentMethod.BOLETO);
        p2.setMethod(PaymentMethod.PIX);

        order1.setPayment(p1);
        order2.setPayment(p2);



    }


    @Test
    void findAllActiveDeveTrazerOsTresUsurariosAtivos() {

        repository.save(user1);
        repository.save(user2);
        repository.save(user3);
        List<User> users = repository.findAllActive();
        Assertions.assertEquals(users.size() ,3);

    }

    @Test
    void findAllActiveDeveTrazerSomenteDoisUsuariosAtivos(){
        user1.setEnabled(false);
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);
        List<User> users = repository.findAllActive();
        Assertions.assertEquals(users.size() ,2);
    }

    @Test
    void findAllActiveDeveTrazerSomenteUmUsuarioAtivo(){
        user1.setEnabled(false);
        user2.setEnabled(false);
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);
        List<User> users = repository.findAllActive();
        Assertions.assertEquals(users.size() ,1);
    }

    @Test
    void findAllNaoDeveTrazerNinguem(){
        user1.setEnabled(false);
        user2.setEnabled(false);
        user3.setEnabled(false);
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);
        List<User> users = repository.findAllActive();
        Assertions.assertEquals(users.size() ,0);
    }



    @Test
    void findByIdWithJobsDeveTrazerOsUsuariosComASuaListaDeJobs() {

        user1 = repository.save(user1);
        user2 = repository.save(user2);
        user3 = repository.save(user3);

        job1.setProvider(user1);
        job2.setProvider(user1);

        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);
        job2.setCategory(cat2);

        manager.persist(job1);
        manager.persist(job2);


        Optional<User> user1ComJob = repository.findByIdWithJobs(user1.getId());
        Assertions.assertEquals(2,user1.getJobs().size());

        Optional<User> user2ComJob = repository.findByIdWithJobs(user2.getId());
        Assertions.assertEquals(0,user2.getJobs().size());

        Optional<User> user3ComJob = repository.findByIdWithJobs(user3.getId());
        Assertions.assertEquals(0,user3.getJobs().size());



    }

    @Test
    void findByIdWithOrdersDeveTrazerOsUsuariosComASuaListaDeOrders() {

        user1 = repository.save(user1);
        user2 = repository.save(user2);
        user3 = repository.save(user3);

        job1.setProvider(user1);
        job2.setProvider(user2);

        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);
        job2.setCategory(cat2);

        job1 = manager.persist(job1);
        job2 = manager.persist(job2);

        order1.setJob(job1);
        order1.setCustomer(user3);

        order2.setJob(job1);
        order2.setCustomer(user2);

        manager.persist(order1);
        manager.flush();

        manager.persist(order2);
        manager.flush();

        manager.persist(order1.getPayment());
        manager.persist(order2.getPayment());

        manager.flush();

        Optional<User> user3ComOrders = repository.findByIdWithOrders(user3.getId());
        Optional<User> user2ComOrders = repository.findByIdWithOrders(user2.getId());

        assertEquals(user3ComOrders.get().getOrders().size(),1);
        assertEquals(user2ComOrders.get().getOrders().size(),1);

        assertEquals(user3ComOrders.get().getOrders().get(0).getJob(),job1);
        assertEquals(user2ComOrders.get().getOrders().get(0).getJob(),job1);


    }
}