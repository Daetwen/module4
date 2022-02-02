package com.epam.esm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String role;

    public UserDto() {}

    public UserDto(Long id, String name, String surname, String login, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.role = role;
    }

    public UserDto(Long id, String name, String surname, String login, String password, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) obj;
        return id.equals(userDto.id) &&
                name.equals(userDto.name) &&
                surname.equals(userDto.surname) &&
                login.equals(userDto.login) &&
                role.equals(userDto.role);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + (id != null ? id.hashCode() : 0);
        result = result * prime + (name != null ? name.hashCode() : 0);
        result = result * prime + (surname != null ? surname.hashCode() : 0);
        result = result * prime + (login != null ? login.hashCode() : 0);
        result = result * prime + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserDto { ")
                .append("id = '").append(id).append('\'')
                .append(", name = '").append(name).append('\'')
                .append(", surname = '").append(surname).append('\'')
                .append(", login = '").append(login).append('\'')
                .append(", role = '").append(role).append('\'');
        return stringBuilder.toString();
    }
}
