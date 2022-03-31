package com.epam.esm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encoder {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
