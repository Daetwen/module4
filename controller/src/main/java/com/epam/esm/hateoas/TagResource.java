package com.epam.esm.hateoas;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface Tag resource.
 */
public interface TagResource {

    /**
     * Hateoas for getOne function in tag controller.
     *
     * @param tagDto the tag dto
     * @return result hateoas for tag
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     * @throws ControllerException        the controller exception
     */
    Resource<TagDto> getOne(TagDto tagDto)
            throws ServiceValidationException, ServiceSearchException, ControllerException;

    /**
     * Hateoas for getAll function in tag controller.
     *
     * @param tagDtoList the tag dto list
     * @param page       the page
     * @param pageSize   the page size
     * @return result hateoas for list of tags
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     * @throws ControllerException        the controller exception
     */
    Resource<List<Resource<TagDto>>> getAll(List<TagDto> tagDtoList, String page, String pageSize)
            throws ServiceValidationException, ServiceSearchException, ControllerException;

    /**
     * Hateoas for delete function in tag controller.
     *
     * @param tagDto the tag dto
     * @return result hateoas for tag
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     * @throws ControllerException        the controller exception
     */
    Resource<TagDto> delete(TagDto tagDto)
            throws ServiceValidationException, ServiceSearchException, ControllerException;
}
