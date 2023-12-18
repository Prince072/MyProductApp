package com.premich.user.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public void registerNewUser(RegistrationRequest request){
        String phoneNumber = request.getRegistration().getPhoneNumber();


        Optional<Registration> optionalRegistration = registrationRepository.selectUserByPhoneNumber(phoneNumber);

        if (optionalRegistration.isPresent()){
            Registration registration = optionalRegistration.get();
            if (registration.getName().equals(request.getRegistration().getName())){
                return;
            }
            throw new IllegalStateException(String.format("this [%s]  is already registered",phoneNumber));

        }
        registrationRepository.save(request.getRegistration());
    }
}
