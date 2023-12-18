package com.premich.user.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


class RegistrationServiceTest {

    @Captor
    ArgumentCaptor<Registration> ArgumentCaptor;

    @Mock
    private RegistrationRepository registrationRepository;


    private RegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new RegistrationService(registrationRepository);
    }
    @Test
    void itShouldRegisterNewCustomer() {
        //given
        String phoneNumber = "08208";
        Registration customer = new Registration(UUID.randomUUID(), "Mel", phoneNumber);

        RegistrationRequest request = new RegistrationRequest(customer);

        given(registrationRepository.selectUserByPhoneNumber(phoneNumber)).willReturn(Optional.empty());

        //when
        underTest.registerNewUser(request);


        //then
        then(registrationRepository).should().save(ArgumentCaptor.capture());
        Registration value = ArgumentCaptor.getValue();
        assertThat(value).isEqualToComparingFieldByField(customer);
    }
}