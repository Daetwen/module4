package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private static final String PASSWORD = "$2a$10$zRWF3T6onPXqy1CDIQBPvOouwUFpridaWGtal9tCsz1SAillVIGUm";

    private UserDao userDao;
    private Validator validator;
    private Verifier verifier;
    private UserConverter userConverter;
    private UserService userService;
    private Encoder encoder;
    private PasswordEncoder passwordEncoder;
    private UserDto userDtoTest1;
    private User userTest1;

    @BeforeEach
    public void setUp() {
        userDao = mock(UserDao.class);
        userConverter = mock(UserConverter.class);
        encoder = mock(Encoder.class);
        passwordEncoder = mock(PasswordEncoder.class);
        validator = mock(Validator.class);
        verifier = mock(Verifier.class);
        userService = new UserServiceImpl(userDao, validator, verifier, userConverter, encoder);
        List<Order> orderList = new ArrayList<>();
        userDtoTest1 = new UserDto(5L, "Vlad", "Makarei", "Daetwen", "USER");
        userTest1 = new User(5L, "Vlad", "Makarei", "Daetwen", PASSWORD, orderList, new Role("USER"));
    }

    @Test
    public void registerTestFalseIfUserNotValid() throws ServiceSearchException {
        when(verifier.isValidUser(any(UserDto.class))).thenReturn(false);
        doThrow(ServiceSearchException.class).when(validator).validateUser(nullable(User.class), anyString());
        assertThrows(ServiceSearchException.class,
                () -> userService.register(userDtoTest1));
    }

    @Test
    public void registerTestFalseIfUserNotCreate() throws ServiceSearchException {
        when(verifier.isValidUser(any(UserDto.class))).thenReturn(true);
        when(encoder.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(anyString())).thenReturn(PASSWORD);
        when(userDao.save(any(User.class))).thenReturn(null);
        doThrow(ServiceSearchException.class).when(validator).validateUser(nullable(User.class), anyString());
        assertThrows(ServiceSearchException.class,
                () -> userService.register(userDtoTest1));
    }

    @Test
    public void findByLoginTestTrue() throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateLogin(anyString());
        when(userDao.findByLogin(anyString())).thenReturn(userTest1);
        doNothing().when(validator).validateUserWithPassword(any(User.class), anyString());
        when(userConverter.convertUserToUserDtoWithPassword(any(User.class))).thenReturn(userDtoTest1);
        UserDto actual = userService.findByLogin("Daetwen");
        assertEquals(userDtoTest1, actual);
    }

    @Test
    public void findByLoginTestFalseIfLoginNotValid() throws ServiceValidationException {
        doThrow(ServiceValidationException.class).when(validator).validateLogin(anyString());
        assertThrows(ServiceValidationException.class,
                () -> userService.findByLogin("f5g65&*&$#@@@@@"));
    }

    @Test
    public void findByLoginTestFalseIfUserNotFound()
            throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateLogin(anyString());
        when(userDao.findByLogin(anyString())).thenReturn(null);
        doThrow(ServiceSearchException.class).when(validator)
                .validateUserWithPassword(nullable(User.class), anyString());
        assertThrows(ServiceSearchException.class,
                () -> userService.findByLogin("Daethwen"));
    }

    @Test
    public void findByLoginAndPasswordTestTrue() throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateLogin(anyString());
        when(userDao.findByLogin(anyString())).thenReturn(userTest1);
        doNothing().when(validator).validateUser(any(User.class), anyString(), anyString());
        when(userConverter.convertUserToUserDto(any(User.class))).thenReturn(userDtoTest1);
        UserDto actual = userService.findByLoginAndPassword("Daetwen", PASSWORD);
        assertEquals(userDtoTest1, actual);
    }

    @Test
    public void findByLoginAndPasswordTestFalseIfLoginNotValid() throws ServiceValidationException {
        doThrow(ServiceValidationException.class).when(validator).validateLogin(anyString());
        assertThrows(ServiceValidationException.class,
                () -> userService.findByLoginAndPassword("f5g65&*&$#@@@@@", PASSWORD));
    }

    @Test
    public void findByLoginAndPasswordTestFalseIfUserNotFound()
            throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateLogin(anyString());
        when(userDao.findByLogin(anyString())).thenReturn(null);
        doThrow(ServiceSearchException.class).when(validator)
                .validateUser(nullable(User.class), anyString(), anyString());
        assertThrows(ServiceSearchException.class,
                () -> userService.findByLoginAndPassword("Daethwen", PASSWORD));
    }

    @Test
    public void findByIdTestTrue() throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateId(anyString());
        when(userDao.findById(anyLong())).thenReturn(Optional.of(userTest1));
        when(userConverter.convertUserToUserDto(any(User.class))).thenReturn(userDtoTest1);
        UserDto actual = userService.findById("5");
        assertEquals(userDtoTest1, actual);
    }

    @Test
    public void findByIdTestFalseIfIdNotValid() throws ServiceValidationException {
        doThrow(ServiceValidationException.class).when(validator).validateId(anyString());
        assertThrows(ServiceValidationException.class,
                () -> userService.findById("f5g"));
    }

    @Test
    public void findByIdTestFalseIfUserNotFound()
            throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateId(anyString());
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        doThrow(ServiceSearchException.class).when(validator).validateUser(any(Optional.class), anyString());
        assertThrows(ServiceSearchException.class,
                () -> userService.findById("6"));
    }

    @Test
    public void findAllTestTrue() throws ServiceValidationException {
        List<UserDto> expected = new ArrayList<>();
        expected.add(userDtoTest1);
        List<User> listForWork = new ArrayList<>();
        listForWork.add(userTest1);
        Page<User> page = new PageImpl<>(listForWork);
        doNothing().when(validator).validatePage(anyString());
        when(userDao.findAll(any(Pageable.class))).thenReturn(page);
        when(userConverter.convertUserToUserDto(any(User.class))).thenReturn(userDtoTest1);
        List<UserDto> actual = userService.findAll("1", "10");
        assertEquals(expected, actual);
    }

    @Test
    public void findAllTestFalseIfPageNotValid() throws ServiceValidationException {
        doThrow(ServiceValidationException.class).when(validator).validatePage(anyString());
        assertThrows(ServiceValidationException.class,
                () -> userService.findAll("1", "10"));
    }
}
