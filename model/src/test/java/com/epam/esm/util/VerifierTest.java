package com.epam.esm.util;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class VerifierTest {

    Verifier verifier;

    @BeforeEach
    public void setUp() {
        UserDao userDao = mock(UserDao.class);
        CertificateDao certificateDao = mock(CertificateDao.class);
        verifier = new Verifier(userDao, certificateDao);
    }


    @Test
    public void isValidIdTestTrue() {
        boolean actual = verifier.isValidId(1L);
        assertTrue(actual);
    }

    @Test
    public void isValidIdTestFalseIfIdIs0() {
        boolean actual = verifier.isValidId(0L);
        assertFalse(actual);
    }

    @Test
    public void isValidIdTestFalseIfIdNegativeNumber() {
        boolean actual = verifier.isValidId(-1L);
        assertFalse(actual);
    }

    @Test
    public void isValidIdTestFalseIfIdIsNull() {
        boolean actual = verifier.isValidId(null);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestTrue() {
        String name = "Vlad";
        boolean actual = verifier.isValidName(name);
        assertTrue(actual);
    }

    @Test
    public void isValidNameTestTrueIfNameAlmostBorderline() {
        String name = "VladVladVladVladVladVladVladVladVladVladVladV";
        boolean actual = verifier.isValidName(name);
        assertTrue(actual);
    }

    @Test
    public void isValidNameTestFalseIfNameWrittenInCyrillic() {
        String name = "владислав";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalseIfNameIsEmpty() {
        String name = "";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalseIfNameContainsOnlySpaces() {
        String name = "  ";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalseIfNameContainsInvalidCharacters() {
        String name = "<script>";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalseIfNameTooLong() {
        String name = "VladVladVladVladVladVladVladVladVladVladVladVlad";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestTrueIfOneWord() {
        String description= "Hello";
        boolean actual = verifier.isValidDescription(description);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestTrueIfSomeWords() {
        String description= "something new";
        boolean actual = verifier.isValidDescription(description);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestTrueIfDescriptionIsNull() {
        boolean actual = verifier.isValidDescription(null);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestFalseIfDescriptionIsEmpty() {
        String description= "";
        boolean actual = verifier.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestFalseIfDescriptionContainsOnlySpaces() {
        String description= "  ";
        boolean actual = verifier.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestFalseIfDescriptionContainsInvalidCharacters() {
        String description= "D  <script>";
        boolean actual = verifier.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidPriceTestTrue() {
        BigDecimal price = new BigDecimal("1000000");
        boolean actual = verifier.isValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void isValidPriceTestTrueIfPriceIs0() {
        BigDecimal price = new BigDecimal("0");
        boolean actual = verifier.isValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void isValidPriceTestFalseIfPriceIsNegativeNumber() {
        BigDecimal price = new BigDecimal("-1000000");
        boolean actual = verifier.isValidPrice(price);
        assertFalse(actual);
    }

    @Test
    public void isValidDurationTestTrue() {
        int duration = 50;
        boolean actual = verifier.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestTrueIfBigNumber() {
        int duration = 2147483647;
        boolean actual = verifier.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestTrueIfIs0() {
        int duration = 0;
        boolean actual = verifier.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestFalseIfNegativeNumber() {
        int duration = -1;
        boolean actual = verifier.isValidDuration(duration);
        assertFalse(actual);
    }
}
