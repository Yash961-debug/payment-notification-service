package com.example.paymentnotificationservice.service;

public interface NotificationService {

    void sendPaymentCompletedNotification(Long paymentId);

    void sendPaymentReturnedNotification(Long paymentId);
}