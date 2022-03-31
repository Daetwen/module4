package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Register user.
     *
     * @param userDto the user dto
     * @return the registered user dto
     * @throws ServiceSearchException the service search exception
     */
    UserDto register(UserDto userDto) throws ServiceSearchException;

    /**
     * Find user by login.
     *
     * @param login the login
     * @return the user dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    UserDto findByLogin(String login) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find user by login and password.
     *
     * @param login    the login
     * @param password the password
     * @return the user dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    UserDto findByLoginAndPassword(String login, String password) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find user by id.
     *
     * @param id the id for search
     * @return the user dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    UserDto findById(String id) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find all users.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list of all users
     * @throws ServiceValidationException the service validation exception
     */
    List<UserDto> findAll(String page, String pageSize) throws ServiceValidationException;
}
