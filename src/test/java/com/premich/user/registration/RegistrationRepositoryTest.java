package com.premich.user.registration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class RegistrationRepositoryTest {

    @Autowired
    private RegistrationRepository underTest;

    @Test
    void itShouldSelectUserByPhoneNumber() {
        //given
        String phoneNumber = "082";
        UUID id = UUID.randomUUID();
        Registration user = new Registration(id, "Mac", phoneNumber);
        //when
        underTest.save(user);
        //then
        Optional<Registration> optionalRegistration = underTest.findById(id);
        assertThat(optionalRegistration).isPresent()
                .hasValueSatisfying(r -> {
//                    assertThat(r.getPhoneNumber().equals(phoneNumber));
//                    assertThat(r.getName().equals("mac"));
//                    assertThat(r.getId().equals(id));
                    assertThat(r).isEqualToComparingFieldByField(user);

                });
    }
}