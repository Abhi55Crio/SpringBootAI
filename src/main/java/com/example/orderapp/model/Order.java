package com.example.orderapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String item;

    private Integer quantity;

    private BigDecimal price;

    private String status;

    private LocalDateTime createdAt;

    public Order() {}

    public Order(String customerName, String item, Integer quantity, BigDecimal price, String status) {
        this.customerName = customerName;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    // no setId on purpose to let JPA generate it
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Optionally allow setting createdAt (for import), otherwise JPA sets it
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}