package com.premich.customer.Customer;

import org.springframework.web.bind.annotation.PathVariable;

public class CustomerRegistrationRequest {

    private Customer customer;

    public CustomerRegistrationRequest(@PathVariable("registration") Customer customer) {
        this.customer = customer;
    }

    public Customer getRegistration() {
        return customer;
    }

    @Override
    public String toString() {
        return "CustomerRegistrationRequest{" +
                "customer=" + customer +
                '}';
    }

    public Customer getCustomer() {
        return customer;

    }
}
