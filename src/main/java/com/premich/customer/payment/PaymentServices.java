package com.premich.customer.payment;

import com.premich.customer.Customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServices {


    private final List<Currency> AVAIlABLE_CURRENCY = List.of(Currency.USD,Currency.ZAR);
    private CustomerRepository customerRepository;
    private PaymentRepository paymentRepository;
    private CardPaymentCharger cardPaymentCharger;

    public PaymentServices(CustomerRepository customerRepository, PaymentRepository paymentRepository, CardPaymentCharger cardPaymentCharger) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.cardPaymentCharger = cardPaymentCharger;
    }

    void chargeCard(UUID customerId,PaymentRequest paymentRequest) {
        //Does the customer exist
        boolean isCustomerFound = customerRepository.findById(customerId).isPresent();
        if (!isCustomerFound) {
            throw new IllegalStateException(String.format("Customer with [%s] id not found", customerId));
        }

        //Do we support the currency ?
        boolean isCurrencySupported = AVAIlABLE_CURRENCY.stream()
                .anyMatch(c -> c.equals(paymentRequest.getPayment().getCurrency()));

        if (!isCurrencySupported) {
            throw new IllegalStateException(String.format("the currency is not supported"));

        }

        // Charge card
        cardPaymentCharge cardPaymentCharge = cardPaymentCharger.chargeCard(
                paymentRequest.getPayment().getSource(),
                paymentRequest.getPayment().getAmount(), paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription()
        );
        if (!cardPaymentCharge.isCardDebited()){
            throw new IllegalStateException(String.format("we can not charge the card",customerId));
        }
        paymentRequest.getPayment().setCustomerId(customerId);

        paymentRepository.save(paymentRequest.getPayment());


    }
}
