package com.example.paymentnotificationservice;

import com.example.paymentnotificationservice.dto.PaymentRequest;
import com.example.paymentnotificationservice.dto.PaymentResponse;
import com.example.paymentnotificationservice.entity.Payment;
import com.example.paymentnotificationservice.entity.PaymentMethod;
import com.example.paymentnotificationservice.entity.PaymentStatus;
import com.example.paymentnotificationservice.exception.PaymentNotFoundException;
import com.example.paymentnotificationservice.repository.PaymentRepository;
import com.example.paymentnotificationservice.service.NotificationService;
import com.example.paymentnotificationservice.service.PaymentServiceImpl;
import com.example.paymentnotificationservice.service.validator.PaymentStateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    private PaymentRepository paymentRepository;
    private PaymentStateValidator validator;
    private NotificationService notificationService;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setup() {
        paymentRepository = mock(PaymentRepository.class);
        validator = mock(PaymentStateValidator.class);
        notificationService = mock(NotificationService.class);

        paymentService = new PaymentServiceImpl(
                paymentRepository,
                validator,
                notificationService
        );
    }

    @Test
    void shouldCreatePayment() {

        PaymentRequest request = new PaymentRequest();
        request.setApplicationId("APP1001");
        request.setAmount(2500.0);
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setApplicationId("APP1001");
        payment.setAmount(2500.0);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setStatus(PaymentStatus.SCHEDULING);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse response = paymentService.notifyPayment(request);

        assertEquals(1L, response.getId());
        assertEquals(PaymentStatus.SCHEDULING, response.getStatus());

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void shouldReturnPayment() {

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setApplicationId("APP1001");
        payment.setAmount(2500.0);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setStatus(PaymentStatus.SCHEDULING);

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getPayment(1L);

        assertEquals("APP1001", response.getApplicationId());
    }

    @Test
    void shouldThrowPaymentNotFound() {

        when(paymentRepository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentNotFoundException.class,
                () -> paymentService.getPayment(100L)
        );
    }

    @Test
    void shouldUpdateStatusToProcessing() {

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.SCHEDULING);

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        PaymentResponse response =
                paymentService.updatePaymentStatus(
                        1L,
                        PaymentStatus.PROCESSING
                );

        assertEquals(
                PaymentStatus.PROCESSING,
                response.getStatus()
        );

        verify(validator)
                .validate(
                        PaymentStatus.SCHEDULING,
                        PaymentStatus.PROCESSING
                );
    }

    @Test
    void shouldSendCompletedNotification() {

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.PROCESSING);

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        paymentService.updatePaymentStatus(
                1L,
                PaymentStatus.COMPLETED
        );

        verify(notificationService)
                .sendPaymentCompletedNotification(1L);
    }

    @Test
    void shouldSendReturnedNotification() {

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.PROCESSING);

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        paymentService.updatePaymentStatus(
                1L,
                PaymentStatus.RETURNED
        );

        verify(notificationService)
                .sendPaymentReturnedNotification(1L);
    }

    @Test
    void shouldGetPaymentsByStatus() {

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setApplicationId("APP1001");
        payment.setAmount(2500.0);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setStatus(PaymentStatus.PROCESSING);

        when(paymentRepository.findByStatus(PaymentStatus.PROCESSING))
                .thenReturn(List.of(payment));

        List<PaymentResponse> responses =
                paymentService.getPaymentsByStatus(
                        PaymentStatus.PROCESSING
                );

        assertEquals(1, responses.size());
        assertEquals(
                PaymentStatus.PROCESSING,
                responses.get(0).getStatus()
        );
    }
}