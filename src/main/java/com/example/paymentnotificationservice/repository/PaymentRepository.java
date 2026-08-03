package com.example.paymentnotificationservice.repository;

import com.example.paymentnotificationservice.entity.Payment;
import com.example.paymentnotificationservice.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatus(PaymentStatus status);

    @Query("""
        SELECT p FROM Payment p
        WHERE p.applicationId = :applicationId
        AND p.status IN (
            com.example.paymentnotificationservice.entity.PaymentStatus.FAILED,
            com.example.paymentnotificationservice.entity.PaymentStatus.RETURNED
        )
    """)
    List<Payment> findFailedOrReturnedPayments(
            @Param("applicationId") String applicationId);
}