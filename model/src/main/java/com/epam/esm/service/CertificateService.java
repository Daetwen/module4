package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;
import java.util.Map;

/**
 * The interface Certificate service.
 */
public interface CertificateService {

    /**
     * Create one certificate.
     *
     * @param certificateDto the certificate dto to create
     * @return the created certificate dto
     * @throws ServiceSearchException the service search exception
     */
    CertificateDto create(CertificateDto certificateDto) throws ServiceSearchException;

    /**
     * Find by id one certificate dto.
     *
     * @param id the id for search
     * @return the certificate dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    CertificateDto findById(String id) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find all certificates.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list of all certificates in the database
     * @throws ServiceValidationException the service validation exception
     */
    List<CertificateDto> findAll(String page, String pageSize) throws ServiceValidationException;

    /**
     * Find all certificates with the required parameters.
     *
     * @param parameters the parameters for search and sort
     * @param page       the page
     * @param pageSize   the page size
     * @return the list of found certificates
     * @throws ServiceValidationException the service validation exception
     */
    List<CertificateDto> findByParameters(Map<String, String> parameters, String page, String pageSize)
            throws ServiceValidationException;

    /**
     * update one certificate.
     *
     * @param certificateDto the certificate dto from which the parameters for the update will be taken
     * @return renewed certificate
     * @throws ServiceSearchException the service search exception
     */
    CertificateDto update(CertificateDto certificateDto) throws ServiceSearchException;

    /**
     * Delete certificate by id.
     *
     * @param id the id
     * @return the deleted certificate dto
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    CertificateDto deleteById(String id) throws ServiceValidationException, ServiceSearchException;
}
