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
    public void ValidateIdTestFalse1() {
        String id = "1231231444444322343242324979876767687687687686768768768768767686876876876868768768";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalse2() {
        String id = "1t23fge12";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalse3() {
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId((String) null));
    }

    @Test
    public void ValidateIdTestFalse4() {
        String id = "";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalse5() {
        String id = "   ";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void ValidateIdTestFalse6() {
        Long id = -5L;
        assertThrows(ServiceValidationException.class,
                () -> validator.validateId(id));
    }

    @Test
    public void validatePageTestFalse1() {
        String page = "1231231444444322343242324979876767687687687686768768768768767686876876876868768768";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalse2() {
        String page = "1t23fge12";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalse3() {
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(null));
    }

    @Test
    public void validatePageTestFalse4() {
        String page = "";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalse5() {
        String page = "   ";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalse6() {
        String page = "-5";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void validatePageTestFalse7() {
        String page = "0";
        assertThrows(ServiceValidationException.class,
                () -> validator.validatePage(page));
    }

    @Test
    public void ValidateNameTestFalse1() {
        String name = "";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(name));
    }

    @Test
    public void ValidateNameTestFalse2() {
        String name = "   ";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(name));
    }

    @Test
    public void ValidateNameTestFalse3() {
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(null));
    }

    @Test
    public void ValidateNameTestFalse4() {
        String name = "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
        assertThrows(ServiceValidationException.class,
                () -> validator.validateName(name));
    }
}
