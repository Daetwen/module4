package com.epam.esm.service.impl;

import com.epam.esm.builder.CertificateBuilder;
import com.epam.esm.builder.CertificateDtoBuilder;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CertificateServiceImplTest {

    private CertificateService certificateService;
    private Validator validator;
    private Verifier verifier;
    private CertificateDao certificateDao;
    private CertificateConverter certificateConverter;
    private CertificateDto certificateDtoTest1;
    private Certificate certificateTest1;

    @BeforeEach
    public void setUp() {
        validator = mock(Validator.class);
        verifier = mock(Verifier.class);
        certificateDao = mock(CertificateDao.class);
        TagDao tagDao = mock(TagDao.class);
        certificateConverter = mock(CertificateConverter.class);
        TagConverter tagConverter = mock(TagConverter.class);
        certificateService = new CertificateServiceImpl(certificateDao, tagDao, validator, verifier,
                certificateConverter, tagConverter);

        Tag tagTest1 = new Tag(5L, "New Year");
        TagDto tagDtoTest1 = new TagDto(5L, "New Year");
        List<TagDto> tagDtoList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        tagDtoList.add(tagDtoTest1);
        tagList.add(tagTest1);

        CertificateDtoBuilder certificateDtoBuilder = new CertificateDtoBuilder();
        certificateDtoBuilder.setId(2L);
        certificateDtoBuilder.setName("Name");
        certificateDtoBuilder.setDescription("Description");
        certificateDtoBuilder.setPrice(new BigDecimal(5000));
        certificateDtoBuilder.setDuration(100);
        certificateDtoBuilder.setCreateDate(OffsetDateTime.parse("2021-11-20T21:30:19+03:00"));
        certificateDtoBuilder.setLastUpdateDate(OffsetDateTime.parse("2021-11-22T21:25:37+03:00"));
        certificateDtoTest1 = certificateDtoBuilder.build();
        certificateDtoTest1.setTags(tagDtoList);

        CertificateBuilder certificateBuilder = new CertificateBuilder();
        certificateBuilder.setId(2L);
        certificateBuilder.setName("Name");
        certificateBuilder.setDescription("Description");
        certificateBuilder.setPrice(new BigDecimal(5000));
        certificateBuilder.setDuration(100);
        certificateBuilder.setCreateDate(OffsetDateTime.parse("2021-11-20T21:30:19+03:00"));
        certificateBuilder.setLastUpdateDate(OffsetDateTime.parse("2021-11-22T21:25:37+03:00"));
        certificateTest1 = certificateBuilder.build();
        certificateTest1.setTags(tagList);
    }

    @Test
    public void createTestTrue() throws ServiceSearchException {
        CertificateDto expected = certificateDtoTest1;
        when(verifier.isValidCertificate(any(CertificateDto.class))).thenReturn(true);
        when(certificateConverter.convertCertificateDtoToCertificate(any(CertificateDto.class)))
                .thenReturn(certificateTest1);
        when(certificateDao.save(any(Certificate.class))).thenReturn(certificateTest1);
        doNothing().when(validator).validateCertificate(any(Certificate.class));
        when(certificateConverter.convertCertificateToCertificateDto(any(Certificate.class)))
                .thenReturn(certificateDtoTest1);
        CertificateDto actual = certificateService.create(certificateDtoTest1);
        assertEquals(expected, actual);
    }

    @Test
    public void createTestFalseIfCertificateNotValid() throws ServiceSearchException {
        when(verifier.isValidCertificate(any(CertificateDto.class))).thenReturn(false);
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(nullable(Certificate.class));
        assertThrows(ServiceSearchException.class,
                () -> certificateService.create(certificateDtoTest1));
    }

    @Test
    public void createTestFalseIfCertificateIsNull() throws ServiceSearchException {
        when(verifier.isValidCertificate(any(CertificateDto.class))).thenReturn(true);
        when(certificateDao.save(any(Certificate.class))).thenReturn(null);
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(nullable(Certificate.class));
        assertThrows(ServiceSearchException.class,
                () -> certificateService.create(certificateDtoTest1));
    }

    @Test
    public void findByIdTestTrue() throws ServiceSearchException, ServiceValidationException {
        CertificateDto expected = certificateDtoTest1;
        doNothing().when(validator).validateId(anyString());
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(certificateTest1));
        doNothing().when(validator).validateCertificate(any(Certificate.class));
        when(certificateConverter.convertCertificateToCertificateDto(any(Certificate.class)))
                .thenReturn(certificateDtoTest1);
        CertificateDto actual = certificateService.findById("5");
        assertEquals(expected, actual);
    }

    @Test
    public void findByIdTestFalseIfIdNotValid() throws ServiceValidationException {
        doThrow(ServiceValidationException.class).when(validator).validateId(anyString());
        assertThrows(ServiceValidationException.class,
                () -> certificateService.findById("f5g"));
    }

    @Test
    public void findByIdTestFalseIfCertificateNotFound()
            throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateId(anyString());
        when(certificateDao.findById(anyLong())).thenReturn(Optional.empty());
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(any(Optional.class));
        assertThrows(ServiceSearchException.class,
                () -> certificateService.findById("5"));
    }

    @Test
    public void findAllTestTrue() throws ServiceValidationException {
        List<CertificateDto> expected = new ArrayList<>();
        expected.add(certificateDtoTest1);
        List<Certificate> listForWork = new ArrayList<>();
        listForWork.add(certificateTest1);
        Page<Certificate> page = new PageImpl<>(listForWork);
        when(certificateDao.findAll(any(Pageable.class))).thenReturn(page);
        when(certificateConverter.convertCertificateToCertificateDto(any(Certificate.class)))
                .thenReturn(certificateDtoTest1);
        List<CertificateDto> actual = certificateService.findAll("1", "10");
        assertEquals(expected, actual);
    }

    @Test
    public void updateTestTrue() throws ServiceSearchException {
        CertificateDto expected = certificateDtoTest1;
        when(verifier.isValidCertificateDto(any(CertificateDto.class))).thenReturn(true);
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(certificateTest1));
        when(certificateDao.save(any(Certificate.class))).thenReturn(certificateTest1);
        doNothing().when(validator).validateCertificate(any(Certificate.class));
        when(certificateConverter.convertCertificateToCertificateDto(any(Certificate.class)))
                .thenReturn(certificateDtoTest1);
        CertificateDto actual = certificateService.update(certificateDtoTest1);
        assertEquals(expected, actual);
    }

    @Test
    public void updateTestFalseIfCertificateNotValid() throws ServiceSearchException {
        when(verifier.isValidCertificate(any(CertificateDto.class))).thenReturn(false);
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(any(Optional.class));
        assertThrows(ServiceSearchException.class,
                () -> certificateService.update(certificateDtoTest1));
    }

    @Test
    public void updateTestFalseIfCertificateNotFound() throws ServiceSearchException {
        when(verifier.isValidCertificate(any(CertificateDto.class))).thenReturn(true);
        when(certificateDao.findById(anyLong())).thenReturn(Optional.empty());
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(any(Optional.class));
        assertThrows(ServiceSearchException.class,
                () -> certificateService.update(certificateDtoTest1));
    }

    @Test
    public void updateTestFalseIfUpdateException() throws ServiceSearchException {
        when(verifier.isValidCertificate(any(CertificateDto.class))).thenReturn(true);
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(certificateTest1));
        when(certificateDao.save(any(Certificate.class))).thenReturn(null);
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(any(Optional.class));
        assertThrows(ServiceSearchException.class,
                () -> certificateService.update(certificateDtoTest1));
    }

    @Test
    public void deleteByIdTestTrue() throws ServiceValidationException, ServiceSearchException {
        CertificateDto expected = certificateDtoTest1;
        doNothing().when(validator).validateId(anyString());
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(certificateTest1));
        doNothing().when(validator).validateCertificate(any(Optional.class));
        doNothing().when(certificateDao).deleteById(anyLong());
        when(certificateConverter.convertCertificateToCertificateDto(any(Certificate.class)))
                .thenReturn(certificateDtoTest1);
        CertificateDto actual = certificateService.deleteById("5");
        assertEquals(expected, actual);
    }

    @Test
    public void deleteByIdTestFalseIfIdNotValid() throws ServiceValidationException {
        doThrow(ServiceValidationException.class).when(validator).validateId(anyString());
        assertThrows(ServiceValidationException.class,
                () -> certificateService.deleteById("5"));
    }

    @Test
    public void deleteByIdTestFalseIfCertificateNotFound()
            throws ServiceValidationException, ServiceSearchException {
        doNothing().when(validator).validateId(anyString());
        when(certificateDao.findById(anyLong())).thenReturn(Optional.empty());
        doThrow(ServiceSearchException.class).when(validator).validateCertificate(any(Optional.class));
        doNothing().when(certificateDao).deleteById(anyLong());
        assertThrows(ServiceSearchException.class,
                () -> certificateService.deleteById("5"));
    }
}
