package com.gamechanger.rai_server.implementation;

import com.gamechanger.rai_server.entity.UserEntity;
import com.gamechanger.rai_server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity();

        userService.createUser(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void testFindUserById() {
        Long userId = 1L;
        UserEntity expectedUser = new UserEntity();
        when(userRepository.findUserById(userId)).thenReturn(expectedUser);

        UserEntity actualUser = userService.findUserById(userId);

        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findUserById(userId);
    }
}
