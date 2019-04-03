package com.stackroute.authenticationservice.service;

import com.stackroute.authenticationservice.Repository.UserRepository;
import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private User user;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUserName("kousik");
        user.setPassword("kousik");

    }

   @Test
   public void testSaveUserSuccess(){

       Mockito.when(userRepository.save(user)).thenReturn(user);
       User fetchuser = userService.saveUser(user);
       Assert.assertEquals(user, fetchuser);
       verify(userRepository, times(1)).save(user);

   }
    @Test
   public void testFindByUserNameAndPassword() throws UserNotFoundException {

        Mockito.when(userRepository.findByUserNameAndPassword(user.getUserName(),user.getPassword())).thenReturn(user);
        User fetchedUser = userService.findByUserNameAndPassword(user.getUserName(),user.getPassword());
       Assert.assertEquals(user, fetchedUser);
       verify(userRepository, times(1)).findByUserNameAndPassword(user.getUserName(),user.getPassword());

   }
}
