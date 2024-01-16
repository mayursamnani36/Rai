package com.gamechanger.rai_server.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.service.UserService;
import com.gamechanger.rai_server.utils.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private RequestValidator requestValidator;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        UserEntity user = new UserEntity("validUser", "validPassword");

        when(requestValidator.validateUser(user)).thenReturn(true);
        doNothing().when(userService).createUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("User Saved Successfully with username validUser"));

        verify(requestValidator, times(1)).validateUser(user);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testCreateUserBadRequest() throws Exception {
        UserEntity user = new UserEntity("short", "short");

        when(requestValidator.validateUser(user)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Please Enter a valid Request with username size between 4 to 10 and password size greater than 8"));

        verify(requestValidator, times(1)).validateUser(user);
        verify(userService, never()).createUser(user);
    }
    @Test
    public void testCreateUserException() throws Exception {
        UserEntity user = new UserEntity("validUser", "validPassword");

        when(requestValidator.validateUser(user)).thenReturn(true);
        doThrow(new RuntimeException("Simulating an exception during user creation")).when(userService).createUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Simulating an exception during user creation"));

        verify(requestValidator, times(1)).validateUser(user);
        verify(userService, times(1)).createUser(user);
    }
}
