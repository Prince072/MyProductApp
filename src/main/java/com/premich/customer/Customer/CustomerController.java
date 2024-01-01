package com.premich.customer.Customer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
public class CustomerController {


    @PutMapping
    public void registerNewCustomer(@RequestBody CustomerRegistrationRequest request){
    }

}
