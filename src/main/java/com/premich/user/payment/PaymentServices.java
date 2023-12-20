package com.premich.user.payment;

import com.premich.user.registration.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServices {
    private final List<Currency> AVAIlABLE_CURRENCY = List.of(Currency.USD,Currency.ZAR);
    private RegistrationRepository registrationRepository;
    private PaymentRepository paymentRepository;
    private CardPaymentCharger cardPaymentCharger;
    @Autowired
    public PaymentServices(RegistrationRepository registrationRepository, PaymentRepository paymentRepository, CardPaymentCharger cardPaymentCharger) {
        this.registrationRepository = registrationRepository;
        this.paymentRepository = paymentRepository;
        this.cardPaymentCharger = cardPaymentCharger;
    }

    void chargeCard(UUID customerId,PaymentRequest paymentRequest) {
        //Does the customer exist
        boolean isCustomerPresent = registrationRepository.existsById(customerId);
        if (!isCustomerPresent) {
            throw new IllegalStateException(String.format("Customer [%s] is already registerd", customerId));
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
        paymentRequest.getPayment().getCustomerId();

        paymentRepository.save(paymentRequest.getPayment());


    }
}
