package com.premich.customer.Customer;

import com.premich.customer.utils.PhoneNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


class CustomerServiceTest {

    @Captor
    ArgumentCaptor<Customer> ArgumentCaptor;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PhoneNumberValidator phoneNumberValidator;


    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new CustomerRegistrationService(customerRepository,phoneNumberValidator);
    }
    @Test
    void itShouldRegisterNewCustomer() {
        //given
        String phoneNumber = "+27720777778";
        Customer customer = new Customer(UUID.randomUUID(), "Mel", phoneNumber);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        given(customerRepository.selectUserByPhoneNumber(phoneNumber)).willReturn(Optional.empty());



        //phone number validator
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        //when
        underTest.registerNewCustomer(request);




        //then
        then(customerRepository).should().save(ArgumentCaptor.capture());
        Customer value = ArgumentCaptor.getValue();
        assertThat(value).isEqualToIgnoringGivenFields(customer,"id");
    }
}