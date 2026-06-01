package com.victorvilar.marketplace.fullstack.domain;

import com.victorvilar.marketplace.fullstack.enums.Reviewer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="tb_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable=false)
    private LocalDate createdAt = LocalDate.now();
    @Column(nullable = false)
    private int rating;
    private String commentary;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Reviewer reviewer;

    @ManyToOne
    private Order order;

}
