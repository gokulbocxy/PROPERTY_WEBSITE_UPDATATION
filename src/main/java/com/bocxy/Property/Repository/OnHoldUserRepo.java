package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.OnHoldUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnHoldUserRepo extends JpaRepository<OnHoldUser, Long> {

//    OnHoldUser findByEmail(String email);

    List<OnHoldUser> findByEmail(String email);


    OnHoldUser findByEmailAndOtp(String email, String otp);
}