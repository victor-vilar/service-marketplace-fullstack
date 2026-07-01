package com.victorvilar.marketplace.fullstack.creators;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.domain.Payment;
import com.victorvilar.marketplace.fullstack.dtos.OrderDTO;
import com.victorvilar.marketplace.fullstack.dtos.PaymentDTO;
import com.victorvilar.marketplace.fullstack.enums.OrderStatus;
import com.victorvilar.marketplace.fullstack.enums.PaymentMethod;
import com.victorvilar.marketplace.fullstack.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class OrderTestCreator {

    public static Order criarUmaOrderCompleta(){
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setOrderStatus(OrderStatus.EM_ANDAMENTO);
        order.setTotalAmount(BigDecimal.TEN);
        order.setObservation("Essa é uma observação");
        order.setJob(JobTestCreator.criarJobCompleto());
        order.setCustomer(UserTestCreator.criarUserCompleto());
        order.setPayment(gerarPayment());
        order.addReview(ReviewTestCreator.criarReviewDoCustomer());
        order.addReview(ReviewTestCreator.criarReviewDoProvider());
        order.setCustomer(UserTestCreator.criarUserCompleto());
        return order;
    }

    public static OrderDTO criarUmaOrderDTOCompleta() {
        PaymentDTO payment = gerarPaymentDTO();
        OrderDTO order = OrderDTO.builder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDate.now())
                .orderStatus(OrderStatus.EM_ANDAMENTO.toString())
                .totalAmount(BigDecimal.TEN)
                .observation("Essa é uma observação")
                .customer(UserTestCreator.criarUserCompleto().getId().toString())
                .job(JobTestCreator.criarJobCompleto().getId().toString())
                .payment(payment)
                .build();
        order.setReviews(List.of(ReviewTestCreator.criarReviewDoCustomer(), ReviewTestCreator.criarReviewDoProvider()));
        return order;
    }

    private static Payment gerarPayment(){
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setMethod(PaymentMethod.BOLETO);
        payment.setStatus(PaymentStatus.EM_ABERTO);
        return payment;
    }

    private static PaymentDTO gerarPaymentDTO(){
        return PaymentDTO.builder()
                .id(UUID.randomUUID().toString())
                .method(PaymentMethod.BOLETO.toString())
                .status(PaymentStatus.EM_ABERTO.toString())
                .build();
    }


}
