package com.epam.esm.hateoas;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface Certificate resource.
 */
public interface CertificateResource {

    /**
     * Hateoas for getOne function in certificate controller.
     *
     * @param certificateDto the certificate dto
     * @return result hateoas for certificate
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<CertificateDto> getOne(CertificateDto certificateDto)
            throws ServiceValidationException, ServiceSearchException;

    /**
     * Hateoas for getAll function in certificate controller.
     *
     * @param certificateDtoList the certificate dto list
     * @param page               the current page
     * @param pageSize           the page size
     * @return result hateoas for list of certificates
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<List<Resource<CertificateDto>>> getAll(List<CertificateDto> certificateDtoList,
                                                    String page,
                                                    String pageSize)
            throws ServiceValidationException, ServiceSearchException;

    /**
     * Hateoas for getByParameters function in certificate controller.
     *
     * @param certificateDtoList the certificate dto list
     * @param tagNames           the tag names
     * @param part               the part of name or description of certificate
     * @param sort               the sort parameter
     * @param page               the current page
     * @param pageSize           the page size
     * @return result hateoas for list of certificates
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<List<Resource<CertificateDto>>> getByParameters(List<CertificateDto> certificateDtoList,
                                                             List<String> tagNames,
                                                             String part,
                                                             String sort,
                                                             String page,
                                                             String pageSize)
            throws ServiceValidationException, ServiceSearchException;

    /**
     * Hateoas for delete function in certificate controller.
     *
     * @param certificateDto the certificate dto
     * @return result hateoas for certificate
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<CertificateDto> delete(CertificateDto certificateDto)
            throws ServiceValidationException, ServiceSearchException;
}
