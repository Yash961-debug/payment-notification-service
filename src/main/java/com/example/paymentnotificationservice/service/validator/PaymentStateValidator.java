package com.example.paymentnotificationservice.service.validator;

import com.example.paymentnotificationservice.entity.PaymentStatus;
import com.example.paymentnotificationservice.exception.InvalidPaymentStateException;
import org.springframework.stereotype.Component;

@Component
public class PaymentStateValidator {

    public void validate(PaymentStatus currentStatus, PaymentStatus newStatus) {

        switch (currentStatus) {

            case SCHEDULING:
                if (newStatus != PaymentStatus.PROCESSING) {
                    throw new InvalidPaymentStateException(
                            "Cannot transition from SCHEDULING to " + newStatus);
                }
                break;

            case PROCESSING:
                if (newStatus != PaymentStatus.COMPLETED &&
                        newStatus != PaymentStatus.FAILED) {
                    throw new InvalidPaymentStateException(
                            "Cannot transition from PROCESSING to " + newStatus);
                }
                break;

            case COMPLETED:
                if (newStatus != PaymentStatus.RETURNED) {
                    throw new InvalidPaymentStateException(
                            "Cannot transition from COMPLETED to " + newStatus);
                }
                break;

            case RETURNED:
            case FAILED:
                throw new InvalidPaymentStateException(
                        "Cannot transition from " + currentStatus + " to " + newStatus);
        }
    }
}