package com.premich.customer.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository underTest;

    @Test
    void itShouldName() {
        //given
        Payment payment = new Payment(1l, UUID.randomUUID(),
                BigDecimal.ONE, Currency.ZAR, "Card", "debit");
        //when
        underTest.save(payment);
        //then
        Optional<Payment> paymentOptional = underTest.findById(payment.getPaymentId());
        assertThat(paymentOptional).isPresent()
                .hasValueSatisfying(p -> {
                    assertThat(p).isEqualToComparingFieldByField(payment);
                });
    }
}