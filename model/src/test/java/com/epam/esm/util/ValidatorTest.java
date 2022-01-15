package com.epam.esm.util;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ServiceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ValidatorTest {

    Validator validator;

    @BeforeEach
    public void setUp() {
        LocaleManager localeManager = mock(LocaleManager.class);
        UserDao userDao = mock(UserDao.class);
        CertificateDao certificateDao = mock(CertificateDao.class);
        validator = new Validator(localeManager, userDao, certificateDao);
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

    @Test
    public void isValidIdTestTrue1() {
        boolean actual = validator.isValidId(1L);
        assertTrue(actual);
    }

    @Test
    public void isValidIdTestFalse1() {
        boolean actual = validator.isValidId(0L);
        assertFalse(actual);
    }

    @Test
    public void isValidIdTestFalse2() {
        boolean actual = validator.isValidId(-1L);
        assertFalse(actual);
    }

    @Test
    public void isValidIdTestFalse3() {
        boolean actual = validator.isValidId(null);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestTrue1() {
        String name = "Vlad";
        boolean actual = validator.isValidName(name);
        assertTrue(actual);
    }

    @Test
    public void isValidNameTestTrue2() {
        String name = "VladVladVladVladVladVladVladVladVladVladVladV";
        boolean actual = validator.isValidName(name);
        assertTrue(actual);
    }

    @Test
    public void isValidNameTestFalse1() {
        String name = "владислав";
        boolean actual = validator.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse2() {
        String name = "";
        boolean actual = validator.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse3() {
        String name = "  ";
        boolean actual = validator.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse4() {
        String name = "<script>";
        boolean actual = validator.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse5() {
        String name = "VladVladVladVladVladVladVladVladVladVladVladVlad";
        boolean actual = validator.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestTrue1() {
        String description= "Hello";
        boolean actual = validator.isValidDescription(description);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestTrue2() {
        String description= "something new";
        boolean actual = validator.isValidDescription(description);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestTrue3() {
        boolean actual = validator.isValidDescription(null);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestFalse1() {
        String description= "";
        boolean actual = validator.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestFalse2() {
        String description= "  ";
        boolean actual = validator.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestFalse3() {
        String description= "D  <script>";
        boolean actual = validator.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidPriceTestTrue1() {
        BigDecimal price = new BigDecimal("1000000");
        boolean actual = validator.isValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void isValidPriceTestTrue2() {
        BigDecimal price = new BigDecimal("0");
        boolean actual = validator.isValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void isValidPriceTestFalse1() {
        BigDecimal price = new BigDecimal("-1000000");
        boolean actual = validator.isValidPrice(price);
        assertFalse(actual);
    }

    @Test
    public void isValidDurationTestTrue1() {
        int duration = 50;
        boolean actual = validator.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestTrue2() {
        int duration = 2147483647;
        boolean actual = validator.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestTrue3() {
        int duration = 0;
        boolean actual = validator.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestFalse1() {
        int duration = -1;
        boolean actual = validator.isValidDuration(duration);
        assertFalse(actual);
    }
}
