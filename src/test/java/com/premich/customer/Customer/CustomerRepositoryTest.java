package com.premich.customer.Customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectUserByPhoneNumber() {
        //given
        String phoneNumber = "082";
        UUID id = UUID.randomUUID();
        Customer user = new Customer(id, "Mac", phoneNumber);
        //when
        underTest.save(user);
        //then
        Optional<Customer> optionalRegistration = underTest.findById(id);
        assertThat(optionalRegistration).isPresent()
                .hasValueSatisfying(r -> {
//                    assertThat(r.getPhoneNumber().equals(phoneNumber));
//                    assertThat(r.getName().equals("mac"));
//                    assertThat(r.getId().equals(id));
                    assertThat(r).isEqualToComparingFieldByField(user);

                });
    }
}