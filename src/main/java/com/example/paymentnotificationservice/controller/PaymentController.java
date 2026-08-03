package com.example.paymentnotificationservice.controller;
import java.util.List;
import com.example.paymentnotificationservice.dto.PaymentRequest;
import com.example.paymentnotificationservice.dto.PaymentResponse;
import com.example.paymentnotificationservice.dto.PaymentStatusUpdateRequest;
import com.example.paymentnotificationservice.entity.PaymentStatus;
import com.example.paymentnotificationservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/notify")
    public ResponseEntity<PaymentResponse> notifyPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.notifyPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long paymentId) {

        PaymentResponse response = paymentService.getPayment(paymentId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long paymentId,
            @Valid @RequestBody PaymentStatusUpdateRequest request) {

        PaymentResponse response =
                paymentService.updatePaymentStatus(paymentId, request.getStatus());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(
            @RequestParam PaymentStatus status) {

        List<PaymentResponse> responses =
                paymentService.getPaymentsByStatus(status);

        return ResponseEntity.ok(responses);
    }
}