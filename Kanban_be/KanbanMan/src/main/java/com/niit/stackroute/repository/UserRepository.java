package com.niit.stackroute.repository;

import com.niit.stackroute.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String > {

    public User findByEmail(String email);
}
