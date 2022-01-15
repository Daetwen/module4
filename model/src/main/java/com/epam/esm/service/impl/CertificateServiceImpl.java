package com.epam.esm.service.impl;

import com.epam.esm.constant.LanguagePath;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class CertificateServiceImpl implements CertificateService {

    private static final String DELIMITER = "#";
    private static final String SORT_PARAMETER = "name";

    private final Validator validator;
    private final CertificateDao certificateDao;
    private final TagDao tagDao;
    private final LocaleManager localeManager;
    private final CertificateConverter certificateConverter;
    private final TagConverter tagConverter;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao, Validator validator,
                                  LocaleManager localeManager, CertificateConverter certificateConverter,
                                  TagConverter tagConverter) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.validator = validator;
        this.localeManager = localeManager;
        this.certificateConverter = certificateConverter;
        this.tagConverter = tagConverter;
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) throws ServiceSearchException {
        Certificate certificate = null;
        if (validator.isValidCertificate(certificateDto)) {
            certificateDto.setCreateDate(OffsetDateTime.now());
            certificateDto.setLastUpdateDate(OffsetDateTime.now());
            certificate = certificateDao.save(
                    certificateConverter.convertCertificateDtoToCertificate(certificateDto));
        }
        return checkCertificate(certificate);
    }

    @Override
    public CertificateDto findById(String id) throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<Certificate> result = certificateDao.findById(Long.parseLong(id));
        return checkCertificate(result);
    }

    @Override
    public List<CertificateDto> findAll(String page, String pageSize)
            throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<CertificateDto> certificateDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPages(pageSize)) {
            for (Certificate element : certificateDao.findAll(
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(pageSize)))) {
                certificateDtoList.add(certificateConverter.convertCertificateToCertificateDto(element));
            }
        }
        return appendTags(certificateDtoList);
    }

    @Override
    public List<CertificateDto> findByParameters(Map<String, String> parameters, String page, String pageSize)
            throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        int currentPage = Integer.parseInt(page);
        int currentPageSize = Integer.parseInt(pageSize);
        List<CertificateDto> certificateDtoList = new ArrayList<>();
        if (currentPage <= getCountOfPages(pageSize)) {
            for (Certificate element :
                    certificateDao.findDistinctCertificateByTagsNameInOrNameLikeOrDescriptionLike(
                            createTagListForSearchByParameters(parameters),
                            createParameterForSearchByPart(parameters),
                            createParameterForSearchByPart(parameters),
                            createParameterForPaginationAndSort(currentPage, currentPageSize, parameters))) {
                certificateDtoList.add(certificateConverter.convertCertificateToCertificateDto(element));
            }
        }

        return appendTags(certificateDtoList);
    }

    @Override
    public CertificateDto update(CertificateDto certificateDto) throws ServiceSearchException {
        Optional<Certificate> certificate = Optional.empty();
        if (validator.isValidCertificateDto(certificateDto)
                && certificateDao.findById(certificateDto.getId()).isPresent()) {
            certificate = certificateDao.findById(certificateDto.getId());
            certificate = Optional.of(
                    certificateDao.save(createCertificateForUpdate(certificate.get(), certificateDto)));
        }
        return checkCertificate(certificate);
    }

    @Override
    public CertificateDto deleteById(String id)
            throws ServiceValidationException, ServiceSearchException {
        validator.validateId(id);
        Optional<Certificate> result = certificateDao.findById(Long.parseLong(id));
        certificateDao.deleteById(Long.parseLong(id));
        return checkCertificate(result);
    }

    private long getCountOfPages(String pageSize) {
        long countOfRecords = certificateDao.count();
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }

    private CertificateDto checkCertificate(Optional<Certificate> certificate)
            throws ServiceSearchException {
        if (certificate.isPresent()) {
            CertificateDto certificateDto =
                    certificateConverter.convertCertificateToCertificateDto(certificate.get());
            appendTagsForOne(certificateDto);
            return certificateDto;
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    private CertificateDto checkCertificate(Certificate certificate)
            throws ServiceSearchException {
        if (!Objects.isNull(certificate)) {
            CertificateDto certificateDto =
                    certificateConverter.convertCertificateToCertificateDto(certificate);
            appendTagsForOne(certificateDto);
            return certificateDto;
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    private List<CertificateDto> appendTags(List<CertificateDto> certificateDtoList) {
        for (CertificateDto certificateDto : certificateDtoList) {
            appendTagsForOne(certificateDto);
        }
        return certificateDtoList;
    }

    private void appendTagsForOne(CertificateDto certificateDto) {
        List<Tag> tags = tagDao.findByCertificatesIs(
                certificateConverter.convertCertificateDtoToCertificate(certificateDto));
        List<TagDto> tagsDto = new ArrayList<>();
        for (Tag tag : tags) {
            tagsDto.add(tagConverter.convertTagToTagDto(tag));
        }
        certificateDto.setTags(tagsDto);
    }

    private Certificate createCertificateForUpdate(Certificate certificate, CertificateDto certificateDto) {
        if (validator.isValidName(certificateDto.getName())) {
            certificate.setName(certificateDto.getName());
        }
        if (validator.isValidDescription(certificateDto.getDescription())) {
            certificate.setDescription(certificateDto.getDescription());
        }
        if (certificateDto.getPrice() != null && validator.isValidPrice(certificateDto.getPrice())) {
            certificate.setPrice(certificateDto.getPrice());
        }
        if (certificateDto.getDuration() != null && validator.isValidDuration(certificateDto.getDuration())) {
            certificate.setDuration(certificateDto.getDuration());
        }
        certificate.setLastUpdateDate(OffsetDateTime.now());
        return certificate;
    }

    private List<String> createTagListForSearchByParameters(Map<String, String> parameters) {
        List<String> tagList = new ArrayList<>();
        int countOfTags = 0;
        while (true) {
            if (parameters.containsKey(ParameterName.TAG_NAME.name() + DELIMITER + countOfTags)) {
                tagList.add(parameters.get(ParameterName.TAG_NAME.name() + DELIMITER + countOfTags));
            } else {
                break;
            }
            countOfTags++;
        }
        return tagList;
    }

    private String createParameterForSearchByPart(Map<String, String> parameters) {
        StringBuilder result = new StringBuilder();
        if (parameters.containsKey(ParameterName.NAME_OR_DESC_PART.name())) {
            result.append("%").append(parameters.get(ParameterName.NAME_OR_DESC_PART.name())).append("%");
        }
        return result.toString();
    }

    private Pageable createParameterForPaginationAndSort(
            Integer page, Integer pageSize,
            Map<String, String> parameters) {
        Pageable result = null;
        if (parameters.containsKey(ParameterName.SORT.name())) {
            switch (parameters.get(ParameterName.SORT.name()).toLowerCase()) {
                case "asc": {
                    result = PageRequest.of(page - 1, pageSize, Sort.by(SORT_PARAMETER).ascending());
                    break;
                }
                case "desc": {
                    result = PageRequest.of(page - 1, pageSize, Sort.by(SORT_PARAMETER).descending());
                    break;
                }
                default: result = PageRequest.of(page - 1, pageSize);
            }
        }
        return result;
    }
}
