package com.premich.customer.Customer;

import com.premich.customer.utils.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRegistrationService {

    private CustomerRepository customerRepository;
    private PhoneNumberValidator phoneNumberValidator;


   @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository,
                                       PhoneNumberValidator phoneNumberValidator) {
        this.customerRepository = customerRepository;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request){
        String phoneNumber = request.getCustomer().getPhoneNumber();

        if (!phoneNumberValidator.test(phoneNumber)){
            throw new IllegalStateException("Phone Number" + phoneNumber + "is not valid");
        }


        Optional<Customer> optionalRegistration = customerRepository.selectUserByPhoneNumber(phoneNumber);

        if (optionalRegistration.isPresent()){
            Customer customer = optionalRegistration.get();
            if (customer.getName().equals(request.getRegistration().getName())){
                return;
            }
            throw new IllegalStateException(String.format("this [%s]  is already registered",phoneNumber));

        }

        // this to have full control of the id
        if (request.getRegistration().getId() == null){
            request.getRegistration().setId(UUID.randomUUID());

        }
        customerRepository.save(request.getRegistration());
    }
}
