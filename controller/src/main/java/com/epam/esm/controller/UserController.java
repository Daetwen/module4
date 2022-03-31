package com.epam.esm.controller;

import com.epam.esm.dto.TokenDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.hateoas.UserResource;
import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.constant.PaginationParameter.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "${angular.origin.url}")
public class UserController {

    private final UserService userService;
    private final UserResource userResource;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService userService, UserResource userResource, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userResource = userResource;
        this.jwtProvider = jwtProvider;
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody UserDto userDto)
            throws ServiceSearchException, ServiceValidationException {
        UserDto user = userService.findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        return new TokenDto(jwtProvider.generateToken(user.getLogin(), user.getId()));
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<UserDto> create(@RequestBody UserDto userDto)
            throws ServiceSearchException, ServiceValidationException {
        return userResource.getOne(userService.register(userDto));
    }

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<UserDto>>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE_USERS) String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        return userResource.getAll(userService.findAll(page, pageSize), page, pageSize);
    }

    @GetMapping("get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDto> getOne(@PathVariable final String id)
            throws ServiceSearchException, ServiceValidationException {
        return userResource.getOne(userService.findById(id));
    }
}
