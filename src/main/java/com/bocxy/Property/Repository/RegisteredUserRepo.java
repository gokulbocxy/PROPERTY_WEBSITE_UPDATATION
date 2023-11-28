package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredUserRepo extends JpaRepository<RegisteredUser, Long> {

    

    Boolean existsByEmail(String email);

    RegisteredUser findByUsername(String username);
}