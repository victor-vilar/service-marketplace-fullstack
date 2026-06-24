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

@DataJpaTest
@Profile("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private TestEntityManager manager;

    User user1;
    User user2;
    Job job1;
    Job job2;
    Order order1;
    Order order2;
    Order order3;

    @BeforeEach
    void setUp(){
        setUsers();
        setJobs();
        setOrders();
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(job1);
        manager.persist(job2);
        manager.persist(order1);
        manager.persist(order2);
        manager.persist(order3);
    }

    @Test
    void findByStatusIs_DeveEncontrarTodasAsOrdensEmQueOPaymentStatusDePagamentoForIgualAoPassado() {
        //order 1 = em andamento
        //order 2 = concluido
        //order 3 = em andamento

        List<Order> paymentAberto = repository.findByPaymentStatus(PaymentStatus.EM_ABERTO);
        List<Order> paymentRealizado = repository.findByPaymentStatus(PaymentStatus.PAGAMENTO_REALIZADO);

        Assertions.assertEquals(paymentAberto.size(), 2);
        Assertions.assertEquals(paymentAberto.get(0).getPayment().getStatus() , PaymentStatus.EM_ABERTO);
        Assertions.assertEquals(paymentAberto.get(1).getPayment().getStatus() , PaymentStatus.EM_ABERTO);
        Assertions.assertEquals(paymentRealizado.size(), 1);
        Assertions.assertEquals(paymentRealizado.get(0).getPayment().getStatus() , PaymentStatus.PAGAMENTO_REALIZADO);
    }

    @Test
    void findByPaymentMethodIs_DeveEncontrarTodasAsOrdensEmQueOMetodoDePagamentoForIgualAoPassado() {
        // order 1 = boleto
        // order 2 = pix
        // order 3 = cartao de credito


        List<Order> boletoOrders = repository.findByPaymentMethod(PaymentMethod.BOLETO);
        List<Order> pixOrders = repository.findByPaymentMethod(PaymentMethod.PIX);
        List<Order> creditoOrders = repository.findByPaymentMethod(PaymentMethod.CARTAO_CREDITO);

        Assertions.assertEquals(boletoOrders.size(), 1);
        Assertions.assertEquals(pixOrders.size(), 1);
        Assertions.assertEquals(creditoOrders.size(), 1);


    }

    @Test
    void findByCustomerDeveEncontrarTodosAsOrdersEmQueOCustomerEOPassado(){
        //order 1 = user2
        //order 2 = user2
        //order 3 = user2

        List<Order> user1Orders = repository.findByCustomer(user1);
        List<Order> user2Orders = repository.findByCustomer(user2);

        Assertions.assertEquals(user1Orders.size(),0);
        Assertions.assertEquals(user2Orders.size(),3);

        Assertions.assertEquals(user2Orders.get(0).getCustomer(), user2);
        Assertions.assertEquals(user2Orders.get(1).getCustomer(), user2);
        Assertions.assertEquals(user2Orders.get(2).getCustomer(), user2);


    }

    @Test
    void findByJobDeveEncontrarTodosEmQueOJobEOPassado(){
        //order 1 = job1
        //order 2 = job1
        //order 3 = job1

        List<Order> job1Orders = repository.findByJob(job1);
        List<Order> job2Orders = repository.findByJob(job2);

        Assertions.assertEquals(job1Orders.size(),3);
        Assertions.assertEquals(job2Orders.size(),0);
    }

    @Test
    void findByOrderStatusDeveENcontrarComOrderStatusPassado(){
        //order 1 = em andamento
        //order 2 = concluido
        //order 3 = em andamento

        List<Order> emAndamento = repository.findByOrderStatus(OrderStatus.EM_ANDAMENTO);
        List<Order> concluido = repository.findByOrderStatus(OrderStatus.CONCLUIDO);

        Assertions.assertEquals(emAndamento.size(),2);
        Assertions.assertEquals(concluido.size(),1);
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

        job2 = new Job();
        job2.setTitle("job2");
        job2.setDescription("job2");
        job2.setPrice(BigDecimal.valueOf(2000));
        job2.setProvider(user1);
        job2.setCategory(cat2);

    }

    void setOrders(){
        order1 = new Order();
        order2 = new Order();
        order3 = new Order();

        order1.setOrderStatus(OrderStatus.EM_ANDAMENTO);
        order2.setOrderStatus(OrderStatus.CONCLUIDO);
        order3.setOrderStatus(OrderStatus.EM_ANDAMENTO);

        order1.setTotalAmount(BigDecimal.valueOf(1000));
        order2.setTotalAmount(BigDecimal.valueOf(1000));
        order3.setTotalAmount(BigDecimal.valueOf(20000));

        Payment p1 = new Payment();
        Payment p2 = new Payment();
        Payment p3 = new Payment();

        p1.setStatus(PaymentStatus.EM_ABERTO);
        p2.setStatus(PaymentStatus.PAGAMENTO_REALIZADO);
        p3.setStatus(PaymentStatus.EM_ABERTO);

        p1.setMethod(PaymentMethod.BOLETO);
        p2.setMethod(PaymentMethod.PIX);
        p3.setMethod(PaymentMethod.CARTAO_CREDITO);

        order1.setPayment(p1);
        order2.setPayment(p2);
        order3.setPayment(p3);

        order1.setJob(job1);
        order2.setJob(job1);
        order3.setJob(job1);

        order1.setCustomer(user2);
        order2.setCustomer(user2);
        order3.setCustomer(user2);

    }
}