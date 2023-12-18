package com.premich.user.registration;

import org.springframework.web.bind.annotation.PathVariable;

public class RegistrationRequest {

    private Registration registration;

    public RegistrationRequest(@PathVariable("registration") Registration registration) {
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }
}
