package com.premich.user.payment;

import java.math.BigDecimal;

public interface CardPaymentCharger {

    cardPaymentCharge chargeCard(
            String cardSource,
            BigDecimal amount,
            Currency currency,
            String description);

}

