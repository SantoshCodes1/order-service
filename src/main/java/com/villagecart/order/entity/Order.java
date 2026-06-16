package com.villagecart.order.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="customer_id",nullable = false)
    private Long customerId;
    @Column(name ="vendor_id",nullable = false)
    private  Long vendorId;
    @Enumerated(EnumType.STRING) //Stores status as readable string instead of numbers (0)
    @Column(nullable = false)
    private OrderStatus status=OrderStatus.PENDING;
    @Column(name = "total_price",nullable = false,precision = 10,scale = 2)
    private BigDecimal totalPrice;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();
    @OneToMany(mappedBy ="order",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<OrderItem> items=new LinkedHashSet<>();

    public Order() {
    }
public  void addOrderItem(OrderItem item){
        items.add(item);    //adds the item to orders Set
        item.setOrder(this);// sets this order onto item (hibernate/foreign key side)
}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }
}
