package com.services.client.notification;

import java.time.LocalDateTime;

public record NotificationRequest(
        Integer customerId,
        String customerEmail,
        String message
) {
}
