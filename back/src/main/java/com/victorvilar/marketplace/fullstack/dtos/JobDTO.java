package com.victorvilar.marketplace.fullstack.dtos;

import com.victorvilar.marketplace.fullstack.domain.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobDTO{

    private String id = null;
    private String title;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private String category = "";
    private String provider;
    private List<OrderDTO> orders = new ArrayList<>();

    private JobDTO(String id, String title, String description, BigDecimal price, String category, String provider , List<OrderDTO> orders){
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.provider = provider;
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String id = null;
        private String title;
        private String description;
        private BigDecimal price = BigDecimal.ZERO;
        private String category = "";
        private String provider;
        private List<OrderDTO> orders = new ArrayList<>();

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder category(String category){
            this.category = category;
            return this;
        }

        public Builder provider(String provider){
            this.provider = provider;
            return this;
        }

        public Builder orders(List<OrderDTO> orders){
            this.orders = orders;
            return this;
        }

        public JobDTO build(){

            if(this.title == null || this.description == null || this.provider == null){
                throw new IllegalStateException("Um job deve ter um titulo, descrição e provedor");
            }
            return new JobDTO(id,title,description,price,category,provider,orders);
        }

    }
}
