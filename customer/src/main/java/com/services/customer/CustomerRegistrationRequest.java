package com.services.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
