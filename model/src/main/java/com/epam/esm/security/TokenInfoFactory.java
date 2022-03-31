package com.epam.esm.security;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.jwt.JwtUser;

public final class TokenInfoFactory {

    private TokenInfoFactory() {}

    public static JwtUser create(UserDto user) {
        return new JwtUser(
                user.getLogin(),
                user.getPassword(),
                user.getRole());
    }
}
