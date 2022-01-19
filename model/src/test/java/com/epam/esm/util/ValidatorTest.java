package com.epam.esm.util;

import com.epam.esm.exception.ServiceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ValidatorTest {

    Validator validator;
    Encoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        LocaleManager localeManager = mock(LocaleManager.class);
        passwordEncoder = mock(Encoder.class);
        validator = new Validator(localeManager, passwordEncoder);
    }

    @Test
    public void ValidateIdTestFalseIfIdTooLong() {
        String id = "1231231444444322343242324979876767687687687686768768768768767686876876876868768768";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalseIfIdContainsLetters() {
        String id = "1t23fge12";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalseIfIdIsNull() {
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId((String) null));
    }

    @Test
    public void ValidateIdTestFalseIfIdIsEmpty() {
        String id = "";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalseIfIdContainsOnlySpaces() {
        String id = "   ";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalseIfIdNegativeNumber() {
        Long id = -5L;
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void validatePageTestFalseIfPageTooLong() {
        String page = "1231231444444322343242324979876767687687687686768768768768767686876876876868768768";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalseIfPageNumberContainsLetters() {
        String page = "1t23fge12";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalseIfPageIsNull() {
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(null));
    }

    @Test
    public void validatePageTestFalseIfPageNumberIsEmpty() {
        String page = "";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalseIfPageNumberContainsOnlySpaces() {
        String page = "   ";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalseIfPageNegativeNumber() {
        String page = "-5";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalseIfPageNumberIs0() {
        String page = "0";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void ValidateNameTestFalseIfNameIsEmpty() {
        String name = "";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(name));
    }

    @Test
    public void ValidateNameTestFalseIfNameContainsOnlySpaces() {
        String name = "   ";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(name));
    }

    @Test
    public void ValidateNameTestFalseIfNameIsNull() {
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(null));
    }

    @Test
    public void ValidateNameTestFalseIfNameTooLong() {
        String name = "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(name));
    }
}
