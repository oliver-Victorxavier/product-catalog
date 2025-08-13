package com.victorxavier.product_catalog.domain.usecase.notification;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
