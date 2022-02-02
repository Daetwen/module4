package com.epam.esm.util;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convertUserToUserDto(User user) {
        return setCommonUserDtoBuilderParameters(user);
    }

    public UserDto convertUserToUserDtoWithPassword(User user) {
        UserDto userDto = setCommonUserDtoBuilderParameters(user);
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public User convertUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .role(new Role(userDto.getRole()))
                .build();
    }

    private UserDto setCommonUserDtoBuilderParameters(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .role(user.getRole().getName())
                .build();
    }
}
