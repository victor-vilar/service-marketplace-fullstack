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
    private PaymentStatus status = PaymentStatus.EM_ABERTO;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
