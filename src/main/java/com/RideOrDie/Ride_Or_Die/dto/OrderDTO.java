package com.RideOrDie.Ride_Or_Die.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private Double totalPrice;
    private LocalDateTime orderDate;
    private List<Long> productIds;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String customerName, String customerEmail, String shippingAddress, Double totalPrice, LocalDateTime orderDate, List<Long> productIds) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.productIds = productIds;
    }

    // Getterek és Setterek
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
}