package com.epam.esm.util;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ServiceValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Validator {
    private static final String ID_REGEX = "\\d+";
    private static final String NAME_REGEX = "[\\w !?,.]{0,45}";
    private static final String DESCRIPTION_REGEX = "[\\w ,.!?\\-\\d]{0,1000}";
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    private final LocaleManager localeManager;
    private final UserDao userDao;
    private final CertificateDao certificateDao;

    @Autowired
    public Validator(LocaleManager localeManager, UserDao userDao, CertificateDao certificateDao) {
        this.localeManager = localeManager;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
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

    public boolean isValidId(Long id) {
        boolean result = false;
        if (id != null && id >= 1L) {
            result = true;
        }
        return result;
    }

    public boolean isValidName(String name) {
        return StringUtils.isNotBlank(name) && name.matches(NAME_REGEX);
    }

    public boolean isValidDescription(String description) {
        return description == null ||
                (StringUtils.isNotBlank(description) && description.matches(DESCRIPTION_REGEX));
    }

    public boolean isValidPrice(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isValidDuration(int duration) {
        return duration >= 0;
    }

    public boolean isValidRole(String role) {
        return role.equalsIgnoreCase(ROLE_USER) || role.equalsIgnoreCase(ROLE_ADMIN);
    }

    public boolean isValidCertificate(CertificateDto certificateDto) {
        return isValidName(certificateDto.getName()) &&
                isValidDescription(certificateDto.getDescription()) &&
                isValidPrice(certificateDto.getPrice()) &&
                isValidDuration(certificateDto.getDuration());
    }

    public boolean isValidCertificateDto(CertificateDto certificateDto) {
        return isValidId(certificateDto.getId()) &&
                (certificateDto.getName() == null || isValidName(certificateDto.getName())) &&
                isValidDescription(certificateDto.getDescription()) &&
                (certificateDto.getPrice() == null || isValidPrice(certificateDto.getPrice())) &&
                (certificateDto.getDuration() == null || isValidDuration(certificateDto.getDuration()));
    }

    public boolean isValidTag(TagDto tagDto) {
        return isValidName(tagDto.getName());
    }

    public boolean isValidUser(UserDto userDto) {
        return isValidName(userDto.getName()) &&
                isValidName(userDto.getSurname()) &&
                isValidRole(userDto.getRole());
    }

    public boolean isUserExist(Long id) {
        return userDao.findById(id).isPresent();
    }

    public boolean isCertificateExist(Long id) {
        return certificateDao.findById(id).isPresent();
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
