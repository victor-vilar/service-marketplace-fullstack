package com.victorvilar.marketplace.fullstack.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa os serviços que um usuário podera oferecer.
 */

@Entity
@Table(name="tb_jobs")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    public Job(){

    }

    public void setId(UUID id){
        if(this.id == null){
            this.id = id;
        }
    }


    public User getProvider() {
        return provider;
    }

    public void setProvider(User user){
        this.provider = user;
        user.addJob(this);
    }


    public void addOrder(Order order){
        if(!orders.contains(order)){
            orders.add(order);
        }
    }

    public List<Order> getOrders(){
        return orders;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public Category getCategory(){
        return category;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }




    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
