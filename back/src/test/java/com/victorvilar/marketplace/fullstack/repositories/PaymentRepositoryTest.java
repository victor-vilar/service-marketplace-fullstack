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

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Profile("test")
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private TestEntityManager manager;

    Payment pay1;
    Payment pay2;
    User user1;
    User user2;
    Job job1;
    Order order1;
    Order order2;

    @BeforeEach
    void setUp(){
        setUsers();
        setJobs();
        setOrders();
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(job1);
        manager.persist(order1);
        manager.persist(order2);
    }

    @Test
    void encontraOsPagamentosPeloStatus() {

        List<Payment> abertos = repository.findByPaymentStatusIs(PaymentStatus.ABERTA);
        List<Payment> concluidos = repository.findByPaymentStatusIs(PaymentStatus.CONCLUIDO);

        Assertions.assertNotNull(abertos);
        Assertions.assertNotNull(concluidos);
        Assertions.assertEquals(abertos.get(0).getPaymentStatus(),PaymentStatus.ABERTA);
        Assertions.assertEquals(concluidos.get(0).getPaymentStatus(),PaymentStatus.CONCLUIDO);

    }

    @Test
    void encontrarPagmaentosPeloMetodoDePagmento(){
        List<Payment> boletos = repository.findByPaymentMethodIs(PaymentMethod.BOLETO);
        List<Payment> pix = repository.findByPaymentMethodIs(PaymentMethod.PIX);

        Assertions.assertNotNull(boletos);
        Assertions.assertNotNull(pix);
        Assertions.assertEquals(boletos.get(0).getPaymentMethod(),PaymentMethod.BOLETO);
        Assertions.assertEquals(pix.get(0).getPaymentMethod(),PaymentMethod.PIX);

    }

    @Test
    void findByPaymentMethodIs() {
    }

    public void setUsers(){
        user1 = new User();
        user2 = new User();

        user1.setEnabled(true);
        user1.setName("Lorem");
        user1.setPassword("sdff");
        user1.setEmail("lorem1@gmail.com");

        user2.setEnabled(true);
        user2.setName("Ipsum");
        user2.setPassword("sdff");
        user2.setEmail("ipsum@gmail.com");

    }

    public void setJobs(){
        job1 = new Job();
        job1.setTitle("Detetização");
        job1.setDescription("detetização de pragas e vetores");
        job1.setPrice(BigDecimal.valueOf(100));
        job1.setProvider(user1);
        Category cat1 = new Category("Mecanico");
        Category cat2 = new Category("Padeiro");

        cat1 = manager.persist(cat1);
        cat2 = manager.persist(cat2);

        job1.setCategory(cat1);

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

        order1.setJob(job1);
        order2.setJob(job1);

        order1.setCustomer(user2);
        order2.setCustomer(user2);



    }
}