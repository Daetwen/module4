package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.hateoas.TagResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagResourceImpl implements TagResource {

    private static final String GET_THIS_TAGS = "getThis";
    private static final String GET_ALL_TAGS = "getAll";
    private static final String GET_MOST_POPULAR_TAG = "getMostPopular";
    private static final String DELETE_TAG_BY_ID = "delete";

    @Override
    public Resource<TagDto> getOne(TagDto tagDto)
            throws ServiceValidationException, ServiceSearchException, ControllerException {
        return addCommonRefsForOne(tagDto, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @Override
    public Resource<List<Resource<TagDto>>> getAll(List<TagDto> tagDtoList, String page, String pageSize)
            throws ServiceValidationException, ServiceSearchException, ControllerException {
        List<Resource<TagDto>> tagDtoResourceList = new ArrayList<>();
        for (TagDto tagDto : tagDtoList) {
            tagDtoResourceList.add(addCommonRefsForOne(tagDto, page, pageSize));
        }
        Resource<List<Resource<TagDto>>> resultResource = new Resource<>(tagDtoResourceList);
        resultResource.add(linkTo(methodOn(TagController.class)
                .getAll(page, pageSize)).withSelfRel());
        resultResource.add(linkTo(methodOn(TagController.class)
                .getMostPopular()).withRel(GET_MOST_POPULAR_TAG));
        return resultResource;
    }

    @Override
    public Resource<TagDto> delete(TagDto tagDto)
            throws ServiceValidationException, ServiceSearchException, ControllerException {
        Resource<TagDto> tagDtoResource = new Resource<>(tagDto);
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)).withRel(GET_ALL_TAGS));
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getMostPopular()).withRel(GET_MOST_POPULAR_TAG));
        return tagDtoResource;
    }

    private Resource<TagDto> addCommonRefsForOne(TagDto tagDto, String page, String pageSize)
            throws ServiceSearchException, ServiceValidationException, ControllerException {
        Resource<TagDto> tagDtoResource = new Resource<>(tagDto);
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getOne(tagDto.getId().toString(), null)).withRel(GET_THIS_TAGS));
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getOne(null, tagDto.getName())).withRel(GET_THIS_TAGS));
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getOne(tagDto.getId().toString(), tagDto.getName())).withRel(GET_THIS_TAGS));
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getAll(page, pageSize)).withRel(GET_ALL_TAGS));
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .getMostPopular()).withRel(GET_MOST_POPULAR_TAG));
        tagDtoResource.add(linkTo(methodOn(TagController.class)
                .delete(tagDto.getId().toString())).withRel(DELETE_TAG_BY_ID));
        return tagDtoResource;
    }
}
