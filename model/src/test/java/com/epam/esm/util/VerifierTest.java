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
    public void isValidIdTestTrue1() {
        boolean actual = verifier.isValidId(1L);
        assertTrue(actual);
    }

    @Test
    public void isValidIdTestFalse1() {
        boolean actual = verifier.isValidId(0L);
        assertFalse(actual);
    }

    @Test
    public void isValidIdTestFalse2() {
        boolean actual = verifier.isValidId(-1L);
        assertFalse(actual);
    }

    @Test
    public void isValidIdTestFalse3() {
        boolean actual = verifier.isValidId(null);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestTrue1() {
        String name = "Vlad";
        boolean actual = verifier.isValidName(name);
        assertTrue(actual);
    }

    @Test
    public void isValidNameTestTrue2() {
        String name = "VladVladVladVladVladVladVladVladVladVladVladV";
        boolean actual = verifier.isValidName(name);
        assertTrue(actual);
    }

    @Test
    public void isValidNameTestFalse1() {
        String name = "владислав";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse2() {
        String name = "";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse3() {
        String name = "  ";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse4() {
        String name = "<script>";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidNameTestFalse5() {
        String name = "VladVladVladVladVladVladVladVladVladVladVladVlad";
        boolean actual = verifier.isValidName(name);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestTrue1() {
        String description= "Hello";
        boolean actual = verifier.isValidDescription(description);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestTrue2() {
        String description= "something new";
        boolean actual = verifier.isValidDescription(description);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestTrue3() {
        boolean actual = verifier.isValidDescription(null);
        assertTrue(actual);
    }

    @Test
    public void isValidDescriptionTestFalse1() {
        String description= "";
        boolean actual = verifier.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestFalse2() {
        String description= "  ";
        boolean actual = verifier.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidDescriptionTestFalse3() {
        String description= "D  <script>";
        boolean actual = verifier.isValidDescription(description);
        assertFalse(actual);
    }

    @Test
    public void isValidPriceTestTrue1() {
        BigDecimal price = new BigDecimal("1000000");
        boolean actual = verifier.isValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void isValidPriceTestTrue2() {
        BigDecimal price = new BigDecimal("0");
        boolean actual = verifier.isValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void isValidPriceTestFalse1() {
        BigDecimal price = new BigDecimal("-1000000");
        boolean actual = verifier.isValidPrice(price);
        assertFalse(actual);
    }

    @Test
    public void isValidDurationTestTrue1() {
        int duration = 50;
        boolean actual = verifier.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestTrue2() {
        int duration = 2147483647;
        boolean actual = verifier.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestTrue3() {
        int duration = 0;
        boolean actual = verifier.isValidDuration(duration);
        assertTrue(actual);
    }

    @Test
    public void isValidDurationTestFalse1() {
        int duration = -1;
        boolean actual = verifier.isValidDuration(duration);
        assertFalse(actual);
    }
}
