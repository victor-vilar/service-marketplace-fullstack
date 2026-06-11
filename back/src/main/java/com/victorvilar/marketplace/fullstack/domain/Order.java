package com.victorvilar.marketplace.fullstack.domain;

import com.victorvilar.marketplace.fullstack.enums.OrderStatus;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa um uma contratao de um serviço por algum usuario
 */
@Entity
@Table(name="tb_orders")
public class Order implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ABERTA;

    @Column(nullable=false)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy="order", cascade = {CascadeType.REMOVE})
    private List<Review> reviews = new ArrayList<>();
    private String observation;

    @OneToOne(cascade=CascadeType.ALL,orphanRemoval = true, mappedBy="order")
    private Payment payment;

    // Usuario que está contratando o serviço
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable=false)
    private User customer;

    //Serviço que será feito nessa ordem
    @ManyToOne
    @JoinColumn(name="job_id", nullable=false)
    private Job job;

   public Order(){

   }

   public UUID getId(){
       return id;
   }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        this.payment.setOrder(this);
    }

    public User getCustomer() {
        return customer;
    }

    //Setter feito para evitar que o usuario contratante seja o mesmo que ira realizar o serviço
    public void setCustomer(User customer){
        if(job == null || !job.getProvider().equals(customer) ){
            this.customer = customer;
            customer.addOrder(this);
        }else {
            logger.error("O cliente selecionado é o mesmo que oferece o serviço !");
        }
    }

    public void setId(UUID id){
        if(this.id == null){
            this.id = id;
        }
    }

    //Setter feito para evitar que o serviço contratado seja do mesmo cliente.
    public void setJob(Job job){
        if(customer == null || !customer.getJobs().contains(job)){
            this.job = job;
            job.addOrder(this);
        }else {
            logger.error("O serviço selecionado é fornecido pelo cliente selecionado");
        }
    }

    public User getUser(){
        return customer;
    }

    public Job getJob(){
        return job;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
