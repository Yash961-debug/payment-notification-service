package com.example.paymentnotificationservice.dto;

import com.example.paymentnotificationservice.entity.PaymentMethod;
import com.example.paymentnotificationservice.entity.PaymentStatus;

public class PaymentResponse {

    private Long id;
    private String applicationId;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus Status;

    public PaymentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return Status;
    }

    public void setStatus(PaymentStatus status) {
        this.Status = status;
    }
}