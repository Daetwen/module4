package com.epam.esm.security;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceSearchException e) {
            throw new UsernameNotFoundException(LanguagePath.ERROR_NOT_FOUND_BY_LOGIN);
        } catch (ServiceValidationException e) {
            throw new UsernameNotFoundException(LanguagePath.ERROR_VALIDATION);
        }
        user.setRole(ROLE_PREFIX + user.getRole());
        return TokenInfoFactory.create(user);
    }
}
