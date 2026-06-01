package com.victorvilar.marketplace.fullstack.domain;

import com.victorvilar.marketplace.fullstack.enums.PaymentMethod;
import com.victorvilar.marketplace.fullstack.enums.PaymentStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="tb_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.ABERTA;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
