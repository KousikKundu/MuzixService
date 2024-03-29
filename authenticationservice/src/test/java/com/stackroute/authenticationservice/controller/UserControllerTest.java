package com.stackroute.authenticationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.service.SecurityTokenGenerator;
import com.stackroute.authenticationservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityTokenGenerator securityTokenGenerator;

    private User user;

    @InjectMocks
    private UserController userController;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc =  MockMvcBuilders.standaloneSetup(userController).build();
        user = new User();
        user.setUserName("kousik");
        user.setPassword("kousik");

    }

    @Test
    public void tesSaveUSer() throws Exception {
        when(userService.saveUser(any())).thenReturn(user);
        mockMvc.perform(post("/api/userservice/save")
        .contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
                .andExpect(status().isCreated()).andDo(print());
    }

    private static  String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try{
            ObjectMapper objectMapper = new ObjectMapper() ;
            String jsonContent = objectMapper.writeValueAsString(obj);
            result = jsonContent;
        }catch(JsonProcessingException e){
            result = "JSON Processing error";
        }
        return result;
    }
}
