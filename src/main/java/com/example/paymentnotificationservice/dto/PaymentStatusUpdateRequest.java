package com.example.paymentnotificationservice.dto;

import com.example.paymentnotificationservice.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public class PaymentStatusUpdateRequest {

    @NotNull
    private PaymentStatus status;

    public PaymentStatusUpdateRequest() {
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}