package com.services.customer;

import com.services.amqp.RabbitMQMessageProducer;
import com.services.client.fraud.FraudCheckResponse;
import com.services.client.fraud.FraudClient;
import com.services.client.notification.NotificationClient;
import com.services.client.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final NotificationClient notificationClient;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo : check if email valid
        // todo : check if email not taken
        customerRepository.saveAndFlush(customer);
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        // todo : make it async (add to queue)
        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(), customer.getEmail(),
                String.format("Hi %s, welcome to microservices tutorial...", customer.getFirstName()));
//        notificationClient.sendNotification(notificationRequest); Dont need anymore after queue

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
