package com.example.paymentnotificationservice.service;

import com.example.paymentnotificationservice.dto.PaymentRequest;
import com.example.paymentnotificationservice.dto.PaymentResponse;
import com.example.paymentnotificationservice.entity.Payment;
import com.example.paymentnotificationservice.entity.PaymentStatus;
import com.example.paymentnotificationservice.exception.PaymentNotFoundException;
import com.example.paymentnotificationservice.repository.PaymentRepository;
import com.example.paymentnotificationservice.service.validator.PaymentStateValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStateValidator paymentStateValidator;
    private final NotificationService notificationService;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            PaymentStateValidator paymentStateValidator,
            NotificationService notificationService) {

        this.paymentRepository = paymentRepository;
        this.paymentStateValidator = paymentStateValidator;
        this.notificationService = notificationService;
    }

    @Override
    public PaymentResponse notifyPayment(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setApplicationId(request.getApplicationId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.SCHEDULING);

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse();
        response.setId(savedPayment.getId());
        response.setApplicationId(savedPayment.getApplicationId());
        response.setAmount(savedPayment.getAmount());
        response.setPaymentMethod(savedPayment.getPaymentMethod());
        response.setStatus(savedPayment.getStatus());

        return response;
    }

    @Override
    public PaymentResponse getPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setApplicationId(payment.getApplicationId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());

        return response;
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        // Validate state transition
        paymentStateValidator.validate(payment.getStatus(), status);

        // Update payment status
        payment.setStatus(status);

        // Save updated payment
        Payment updatedPayment = paymentRepository.save(payment);

        // Send notification
        if (status == PaymentStatus.COMPLETED) {
            notificationService.sendPaymentCompletedNotification(updatedPayment.getId());
        }

        if (status == PaymentStatus.RETURNED) {
            notificationService.sendPaymentReturnedNotification(updatedPayment.getId());
        }

        // Prepare response
        PaymentResponse response = new PaymentResponse();
        response.setId(updatedPayment.getId());
        response.setApplicationId(updatedPayment.getApplicationId());
        response.setAmount(updatedPayment.getAmount());
        response.setPaymentMethod(updatedPayment.getPaymentMethod());
        response.setStatus(updatedPayment.getStatus());

        return response;
    }

    @Override
    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {

        List<Payment> payments = paymentRepository.findByStatus(status);

        return payments.stream().map(payment -> {

            PaymentResponse response = new PaymentResponse();
            response.setId(payment.getId());
            response.setApplicationId(payment.getApplicationId());
            response.setAmount(payment.getAmount());
            response.setPaymentMethod(payment.getPaymentMethod());
            response.setStatus(payment.getStatus());

            return response;

        }).toList();
    }
}