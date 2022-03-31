package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.hateoas.UserResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserResourceImpl implements UserResource {

    private static final String GET_ALL = "getAll";

    @Override
    public Resource<UserDto> getOne(UserDto userDto)
            throws ServiceValidationException, ServiceSearchException {
        return addCommonRefsForOne(userDto, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @Override
    public Resource<List<Resource<UserDto>>> getAll(List<UserDto> userDtoList, String page, String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        List<Resource<UserDto>> userDtoResourceList = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            userDtoResourceList.add(addCommonRefsForOne(userDto, page, pageSize));
        }
        Resource<List<Resource<UserDto>>> resultResource = new Resource<>(userDtoResourceList);
        resultResource.add(linkTo(methodOn(UserController.class)
                .getAll(page, pageSize)).withSelfRel());
        return resultResource;
    }

    private Resource<UserDto> addCommonRefsForOne(UserDto userDto, String page, String pageSize)
            throws ServiceSearchException, ServiceValidationException {
        Resource<UserDto> userDtoResource = new Resource<>(userDto);
        userDtoResource.add(linkTo(methodOn(UserController.class)
                .getOne(userDto.getId().toString())).withSelfRel());
        userDtoResource.add(linkTo(methodOn(UserController.class)
                .getAll(page, pageSize)).withRel(GET_ALL));
        return userDtoResource;
    }
}
