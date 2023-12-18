package com.premich.user.registration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {


    @PutMapping
    public void registerNewUser(@RequestBody RegistrationRequest request){
    }

}
