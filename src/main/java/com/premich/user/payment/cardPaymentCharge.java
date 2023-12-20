package com.premich.user.payment;

public class cardPaymentCharge {

    private boolean isCardDebited;

    public cardPaymentCharge(boolean isCardDebited) {
        this.isCardDebited = isCardDebited;
    }

    public cardPaymentCharge() {
    }

    public boolean isCardDebited() {
        return isCardDebited;
    }

    @Override
    public String toString() {
        return "cardPaymentCharge{" +
                "isCardDebited=" + isCardDebited +
                '}';
    }


}
