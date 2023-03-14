package com.niit.stackroute.repository;

import com.niit.stackroute.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication,String> {
     UserAuthentication findByEmail(String email);
}
