package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {

    /**
     * Create one tag.
     *
     * @param tagDto the tag dto for creating
     * @return the created tag dto
     * @throws ServiceSearchException the service search exception
     */
    TagDto create(TagDto tagDto) throws ServiceSearchException;

    /**
     * Find by id one tag dto.
     *
     * @param id the id for search
     * @return the tag dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    TagDto findById(String id) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find by name tag dto.
     *
     * @param name the name for search
     * @return the tag dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    TagDto findByName(String name) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find all tags.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list of all tags
     * @throws ServiceValidationException the service validation exception
     */
    List<TagDto> findAll(String page, String pageSize) throws ServiceValidationException;

    /**
     * Find most popular tag by count and price of certificates.
     *
     * @return the tag dto
     * @throws ServiceSearchException the service search exception
     */
    TagDto findMostPopular() throws ServiceSearchException;

    /**
     * Delete tag by id.
     *
     * @param id the tag id
     * @return the deleted tag dto
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    TagDto deleteById(String id) throws ServiceValidationException, ServiceSearchException;
}
