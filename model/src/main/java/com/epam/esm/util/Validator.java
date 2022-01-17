package com.epam.esm.util;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class Validator {
    private static final String ID_REGEX = "\\d+";
    private static final String NAME_REGEX = "[\\w !?,.]{0,45}";

    private final LocaleManager localeManager;
    private final Encoder passwordEncoder;

    @Autowired
    public Validator(LocaleManager localeManager, Encoder passwordEncoder) {
        this.localeManager = localeManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateId(String id) throws ServiceValidationException {
        validateLongNumberFromString(id);
    }

    public void validateId(Long id) throws ServiceValidationException {
        validateLongNumber(id);
    }

    public void validatePage(String page) throws ServiceValidationException {
        validateIntegerNumberFromString(page);
    }

    public void validateName(String name) throws ServiceValidationException {
        if (StringUtils.isBlank(name) || !name.matches(NAME_REGEX)) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
    }

    public void validateLogin(String login) throws ServiceValidationException {
        validateName(login);
    }

    public void validateCertificate(Optional<Certificate> certificate)
            throws ServiceSearchException {
        if (!certificate.isPresent()) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    public void validateCertificate(Certificate certificate)
            throws ServiceSearchException {
        if (Objects.isNull(certificate)) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    public void validateOrder(Optional<Order> order)
            throws ServiceSearchException {
        if (!order.isPresent()) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    public void validateOrder(Order order)
            throws ServiceSearchException {
        if (Objects.isNull(order)) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    public void validateTag(Optional<Tag> tag) throws ServiceSearchException {
        if (!tag.isPresent()) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    public void validateTag(Tag tag) throws ServiceSearchException {
        if (Objects.isNull(tag)) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    public void validateUser(User user, String errorMessage)
            throws ServiceSearchException {
        if (Objects.isNull(user)) {
            throw new ServiceSearchException(localeManager.getLocalizedMessage(errorMessage));
        }
    }

    public void validateUser(Optional<User> user, String errorMessage)
            throws ServiceSearchException {
        if (!user.isPresent()) {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(errorMessage));
        }
    }

    public void validateUser(User user, String password, String errorMessage)
            throws ServiceSearchException {
        if (!Objects.isNull(user)) {
            if (!passwordEncoder.passwordEncoder().matches(password, user.getPassword())) {
                throw new ServiceSearchException(
                        localeManager.getLocalizedMessage(errorMessage));
            }
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(errorMessage));
        }
    }

    public void validateUserWithPassword(User user, String errorMessage)
            throws ServiceSearchException {
        if (Objects.isNull(user)) {
            throw new ServiceSearchException(localeManager.getLocalizedMessage(errorMessage));
        }
    }

    private void validateLongNumberFromString(String number) throws ServiceValidationException {
        if(StringUtils.isBlank(number) || !number.matches(ID_REGEX)) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
        try {
            Long localNumber = Long.parseLong(number);
            validateLongNumber(localNumber);
        } catch (NumberFormatException e) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
    }

    private void validateLongNumber(Long number) throws ServiceValidationException {
        if (number == null || number < 1L) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
    }

    private void validateIntegerNumberFromString(String number) throws ServiceValidationException {
        if(StringUtils.isBlank(number) || !number.matches(ID_REGEX)) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
        try {
            Integer localNumber = Integer.valueOf(number);
            validateIntegerNumber(localNumber);
        } catch (NumberFormatException e) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
    }

    private void validateIntegerNumber(Integer number) throws ServiceValidationException {
        if (number == null || number < 1) {
            throw new ServiceValidationException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_VALIDATION));
        }
    }
}
