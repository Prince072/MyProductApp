package com.premich.user.registration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository extends CrudRepository<Registration, UUID> {


    @Query(value = "select id,name,phone_number " +
            "from user where phone_number= :phone_number",nativeQuery = true)
    Optional<Registration> selectUserByPhoneNumber(@Param("phone_number")String phoneNumber);
}
