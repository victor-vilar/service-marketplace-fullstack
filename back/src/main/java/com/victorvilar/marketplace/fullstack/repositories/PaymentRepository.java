package com.victorvilar.marketplace.fullstack.repositories;

import com.victorvilar.marketplace.fullstack.domain.Payment;
import com.victorvilar.marketplace.fullstack.enums.PaymentMethod;
import com.victorvilar.marketplace.fullstack.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByPaymentStatusIs(PaymentStatus status);
    List<Payment> findByPaymentMethodIs(PaymentMethod status);
}
