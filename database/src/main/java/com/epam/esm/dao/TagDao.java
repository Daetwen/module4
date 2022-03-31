package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagDao extends JpaRepository<Tag, Long> {
    /**
     * Find by id one tag.
     *
     * @param id the id for search
     * @return the result of search
     */
    Optional<Tag> findById(Long id);

    /**
     * Find by name one tag.
     *
     * @param name the name for search
     * @return the result of search
     */
    Optional<Tag> findByName(String name);

    /**
     * Find tags by certificate id.
     *
     * @param certificate the certificate for search
     * @return the page of all tags for certificate
     */
    List<Tag> findByCertificatesIs(Certificate certificate);

    /**
     * Find most popular tag by count of use and price of certificates.
     *
     * @return the most popular tag
     */
    @Query(nativeQuery = true,
            value = "SELECT tag_id, tags.name FROM tags " +
            "JOIN gift_certificates_has_tags ON tags_id = tag_id " +
            "JOIN gift_certificates ON gift_certificates_id = certificate_id " +
            "GROUP BY tag_id " +
            "ORDER BY COUNT(tag_id) DESC, SUM(price) DESC LIMIT 1")
    Tag findMostPopular();

    /**
     * Find all tags.
     *
     * @param pageable parameters for pagination
     * @return the page of all tags in the database
     */
    Page<Tag> findAllByOrderByIdDesc(Pageable pageable);

    /**
     * Find count of tag records.
     *
     * @return the number of order records
     */
    long count();
}
