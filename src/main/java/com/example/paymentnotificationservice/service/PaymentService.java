package com.example.paymentnotificationservice.service;
import com.example.paymentnotificationservice.dto.PaymentRequest;
import com.example.paymentnotificationservice.dto.PaymentResponse;
import com.example.paymentnotificationservice.entity.PaymentStatus;

import java.util.List;
public interface PaymentService {
     PaymentResponse notifyPayment(PaymentRequest request);

    PaymentResponse getPayment(Long paymentId);

    PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status);

    List<PaymentResponse> getPaymentsByStatus(PaymentStatus status);

}
