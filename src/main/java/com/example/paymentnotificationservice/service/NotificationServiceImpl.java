package com.example.paymentnotificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendPaymentCompletedNotification(Long paymentId) {
        System.out.println("Payment " + paymentId + " completed - notification sent");
    }

    @Override
    public void sendPaymentReturnedNotification(Long paymentId) {
        System.out.println("Payment " + paymentId + " returned - customer notified");
    }
}
