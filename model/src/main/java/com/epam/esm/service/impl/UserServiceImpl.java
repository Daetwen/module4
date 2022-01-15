package com.epam.esm.service.impl;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Encoder;
import com.epam.esm.util.LocaleManager;
import com.epam.esm.util.UserConverter;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final Validator validator;
    private final LocaleManager localeManager;
    private final UserConverter userConverter;
    private final Encoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, Validator validator, LocaleManager localeManager,
                           UserConverter userConverter, Encoder passwordEncoder) {
        this.userDao = userDao;
        this.validator = validator;
        this.localeManager = localeManager;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(UserDto userDto) throws ServiceSearchException {
        User createdUser = null;
        if (validator.isValidUser(userDto)) {
            userDto.setPassword(passwordEncoder.passwordEncoder().encode(userDto.getPassword()));
            createdUser = userDao.save(userConverter.convertUserDtoToUser(userDto));
        }
        return checkUser(createdUser, LanguagePath.ERROR_CREATION);
    }

    @Override
    public UserDto findByLogin(String login) throws ServiceSearchException, ServiceValidationException {
        validator.validateLogin(login);
        User result = userDao.findByLogin(login);
        return checkUserWithPassword(result, LanguagePath.ERROR_NOT_FOUND_BY_LOGIN);
    }

    @Override
    public UserDto findByLoginAndPassword(String login, String password)
            throws ServiceSearchException, ServiceValidationException {
        UserDto user = findByLogin(login);
        return checkUserDto(user, password, LanguagePath.ERROR_NOT_FOUND_BY_LOGIN_AND_PASSWORD);
    }

    @Override
    public UserDto findById(String id) throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<User> result = userDao.findById(Long.parseLong(id));
        return checkUser(result, LanguagePath.ERROR_NOT_FOUND);
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

    private UserDto checkUser(Optional<User> user, String errorMessage)
            throws ServiceSearchException {
        if (user.isPresent()) {
            return userConverter.convertUserToUserDto(user.get());
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(errorMessage));
        }
    }

    private UserDto checkUser(User user, String errorMessage)
            throws ServiceSearchException {
        if (!Objects.isNull(user)) {
            return userConverter.convertUserToUserDto(user);
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(errorMessage));
        }
    }

    private UserDto checkUserDto(UserDto user, String password, String errorMessage)
            throws ServiceSearchException {
        if (!Objects.isNull(user)) {
            if (passwordEncoder.passwordEncoder().matches(password, user.getPassword())) {
                return user;
            } else {
                throw new ServiceSearchException(
                        localeManager.getLocalizedMessage(errorMessage));
            }
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(errorMessage));
        }
    }

    private UserDto checkUserWithPassword(User user, String errorMessage)
            throws ServiceSearchException {
        if (!Objects.isNull(user)) {
            return userConverter.convertUserToUserDtoWithPassword(user);
        } else {
            throw new ServiceSearchException(localeManager.getLocalizedMessage(errorMessage));
        }
    }

    private long getCountOfPages(String pageSize) {
        long countOfRecords = userDao.count();
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }
}
