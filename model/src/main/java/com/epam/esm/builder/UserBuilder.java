package com.epam.esm.builder;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {

    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;
    private List<Order> orders = new ArrayList<>();

    public User build() {
        User user = new User(id, name, surname, login, password, orders, role);
        this.id = null;
        this.name = null;
        this.surname = null;
        this.login = null;
        this.password = null;
        this.orders = null;
        this.role = null;
        return user;
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

    public void setRole(Role role) {
        this.role = role;
    }
}
