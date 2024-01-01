package com.premich.customer.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class PhoneNumberValidatorTest {

    private PhoneNumberValidator underTest;
    @BeforeEach
    void setUp() {
        underTest = new PhoneNumberValidator();

    }

    @Test
    void itShouldValidatePhoneNumber() {
        //given
        String phoneNumber = "+27720777778";
        //when
        boolean isValid = underTest.test(phoneNumber);
        //then
        assertThat(isValid).isTrue();
    }
    @Test
    @DisplayName("when number is longer then 12")
    void itShouldValidatePhoneNumberWhenIncorrectAndHasLongLength() {
        //given
        String phoneNumber = "+277207777789";
        //when
        boolean isValid = underTest.test(phoneNumber);
        //then
        assertThat(isValid).isFalse();
    }
    @Test
    @DisplayName("When the is no plus sign")
    void itShouldValidatePhoneNumberShouldFailWhenThenThereIsNoPlus() {
        //given
        String phoneNumber = "277207777787";
        //when
        boolean isValid = underTest.test(phoneNumber);
        //then
        assertThat(isValid).isFalse();
    }

}
