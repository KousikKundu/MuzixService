package com.stackroute.authenticationservice.Repository;

import com.stackroute.authenticationservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUserNameAndPassword(String userName, String password);


}
