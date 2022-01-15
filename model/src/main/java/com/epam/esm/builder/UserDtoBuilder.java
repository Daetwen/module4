package com.epam.esm.builder;

import com.epam.esm.dto.UserDto;

public class UserDtoBuilder {

    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String role;

    public UserDto build() {
        UserDto userDto = new UserDto(id, name, surname, login, password, role);
        this.id = null;
        this.name = null;
        this.surname = null;
        this.login = null;
        this.password = null;
        this.role = null;
        return userDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
