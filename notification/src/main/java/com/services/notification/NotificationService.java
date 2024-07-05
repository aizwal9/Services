package com.services.notification;

import com.services.client.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest request){
        Notification notification = Notification.builder()
                .customerId(request.customerId())
                .customerEmail(request.customerEmail())
                .sender("services")
                .message(request.message())
                .sentAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

}
