package com.premich.customer.stripe;

import com.premich.customer.payment.CardPaymentCharger;
import com.premich.customer.payment.Currency;
import com.premich.customer.payment.cardPaymentCharge;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService implements CardPaymentCharger {
    private final RequestOptions requestOptions = RequestOptions.builder()
            .setApiKey("sk_test_4eC39HqLyjWDarjtT1zdp7dc")
            .build();
    @Override
    public cardPaymentCharge chargeCard(String cardSource,
                                        BigDecimal amount,
                                        Currency currency,
                                        String description) {
        Map<String,Object> params = new HashMap<>();
        params.put("amount",amount);
        params.put("currency",currency);
        params.put("source",cardSource);
        params.put("description","My first test charge (create for API docs");

        try {
            Charge charge = Charge.create(params, requestOptions);
            return new cardPaymentCharge(charge.getPaid());
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
