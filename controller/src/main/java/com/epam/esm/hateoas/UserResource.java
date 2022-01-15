package com.epam.esm.hateoas;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface User resource.
 */
public interface UserResource {

    /**
     * Hateoas for getOne function in user controller.
     *
     * @param userDto the user dto
     * @return result hateoas for tag
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<UserDto> getOne(UserDto userDto)
            throws ServiceValidationException, ServiceSearchException;

    /**
     * Hateoas for getAll function in user controller.
     *
     * @param userDtoList the user dto list
     * @param page        the current page
     * @param pageSize    the page size
     * @return result hateoas for list of users
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<List<Resource<UserDto>>> getAll(List<UserDto> userDtoList, String page, String pageSize)
            throws ServiceValidationException, ServiceSearchException;
}
