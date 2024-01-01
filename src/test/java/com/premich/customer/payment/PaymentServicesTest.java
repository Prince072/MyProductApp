package com.premich.customer.payment;


import com.premich.customer.Customer.Customer;
import com.premich.customer.Customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;


class PaymentServicesTest {




    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CardPaymentCharger cardPaymentCharger;

    private PaymentServices underTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PaymentServices(customerRepository,paymentRepository, cardPaymentCharger);
    }

    @Test
    void itShouldChargeCustomer() {
        //given this is the customer information from database
        UUID customerId = UUID.randomUUID();
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));

        Currency currency = Currency.ZAR;


        //this is when the payment request is made by the client
        PaymentRequest paymentRequest = new PaymentRequest(
                new Payment(null,
                        null,
                new BigDecimal("100.00")
                        ,currency,"card123xxx",
                        "donation"));

        // to check all permeters of the card payment charger is available
        given(cardPaymentCharger.chargeCard(paymentRequest.getPayment().getSource(),
                paymentRequest.getPayment().getAmount(), paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription())).willReturn(new cardPaymentCharge(true));


        //when


        underTest.chargeCard(customerId, paymentRequest);

        //then
        ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);

        then(paymentRepository).should().save(paymentArgumentCaptor.capture());

        Payment paymentArgumentCaptorValue = paymentArgumentCaptor.getValue();


        assertThat(paymentArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(paymentRequest.getPayment(),"customerId");

        assertThat(paymentArgumentCaptorValue.getCustomerId().equals(customerId));
    }
}