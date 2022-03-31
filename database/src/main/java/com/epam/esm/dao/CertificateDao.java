package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * The interface Certificate dao.
 */
public interface CertificateDao extends JpaRepository<Certificate, Long> {

    /**
     * Find by id one certificate.
     *
     * @param id the id for search
     * @return the founded certificate
     */
    Optional<Certificate> findById(Long id);

    /**
     * Find all certificates.
     *
     * @param pageable parameters for pagination
     * @return the list of all certificates in the database
     */
    Page<Certificate> findAll(Pageable pageable);

    /**
     * Find all certificates with the required arguments.
     *
     * @param tagsName    the tags name
     * @param name        the certificate part of name
     * @param description the certificate part of description
     * @param pageable    parameters for pagination and sort
     * @return the list of founded certificates
     */
    Page<Certificate> findDistinctCertificateByTagsNameInOrNameLikeOrDescriptionLike(
            Collection<String> tagsName, String name, String description, Pageable pageable);

    /**
     * Find count of certificate records.
     *
     * @return the number of all certificate records
     */
    long count();
}
