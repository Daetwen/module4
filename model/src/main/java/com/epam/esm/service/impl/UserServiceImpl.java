package com.epam.esm.service.impl;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final Validator validator;
    private final Verifier verifier;
    private final UserConverter userConverter;
    private final Encoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, Validator validator, Verifier verifier,
                           UserConverter userConverter, Encoder passwordEncoder) {
        this.userDao = userDao;
        this.validator = validator;
        this.verifier = verifier;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(UserDto userDto) throws ServiceSearchException {
        User createdUser = null;
        if (verifier.isValidUser(userDto)) {
            userDto.setPassword(passwordEncoder.passwordEncoder().encode(userDto.getPassword()));
            createdUser = userDao.save(userConverter.convertUserDtoToUser(userDto));
        }
        validator.validateUser(createdUser, LanguagePath.ERROR_CREATION);
        return userConverter.convertUserToUserDto(createdUser);
    }

    @Override
    public UserDto findByLogin(String login) throws ServiceSearchException, ServiceValidationException {
        validator.validateLogin(login);
        User result = userDao.findByLogin(login);
        validator.validateUserWithPassword(result, LanguagePath.ERROR_NOT_FOUND_BY_LOGIN);
        return userConverter.convertUserToUserDtoWithPassword(result);
    }

    @Override
    public UserDto findByLoginAndPassword(String login, String password)
            throws ServiceSearchException, ServiceValidationException {
        validator.validateLogin(login);
        User user = userDao.findByLogin(login);
        validator.validateUser(user, password, LanguagePath.ERROR_NOT_FOUND_BY_LOGIN_AND_PASSWORD);
        return userConverter.convertUserToUserDto(user);
    }

    @Override
    public UserDto findById(String id) throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<User> result = userDao.findById(Long.parseLong(id));
        validator.validateUser(result, LanguagePath.ERROR_NOT_FOUND);
        return userConverter.convertUserToUserDto(result.get());
    }

    @Override
    public List<UserDto> findAll(String page, String pageSize) throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<UserDto> userDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPages(pageSize)) {
            for (User element : userDao.findAll(
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(pageSize)))) {
                userDtoList.add(userConverter.convertUserToUserDto(element));
            }
        }
        return userDtoList;
    }

    private long getCountOfPages(String pageSize) {
        long countOfRecords = userDao.count();
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }
}
