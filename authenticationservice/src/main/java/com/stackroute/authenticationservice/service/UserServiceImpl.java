package com.stackroute.authenticationservice.service;

import com.stackroute.authenticationservice.Repository.UserRepository;
import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUserNameAndPassword(String userName, String password) throws UserNotFoundException {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if(user == null){
            throw new UserNotFoundException();
        }

        return user;
    }
}
