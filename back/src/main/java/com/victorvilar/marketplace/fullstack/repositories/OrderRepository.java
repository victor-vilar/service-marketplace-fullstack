package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.enums.OrderStatus;
import com.victorvilar.marketplace.fullstack.enums.PaymentMethod;
import com.victorvilar.marketplace.fullstack.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByPaymentStatus(PaymentStatus status);
    List<Order> findByPaymentMethod(PaymentMethod status);
    List<Order> findByCustomer(User user);
    List<Order> findByJob(Job job);
    List<Order> findByOrderStatus(OrderStatus status);
}
