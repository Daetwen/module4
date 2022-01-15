package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.hateoas.TagResource;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.ControllerValidator;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final ControllerValidator controllerValidator;
    private final TagResource tagResource;

    @Autowired
    public TagController(TagService tagService, ControllerValidator controllerValidator, TagResource tagResource) {
        this.tagService = tagService;
        this.controllerValidator = controllerValidator;
        this.tagResource = tagResource;
    }

    @RequestMapping(value="/create",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<TagDto> create(@RequestBody TagDto tagDto)
            throws ServiceSearchException, ServiceValidationException, ControllerException {
        return tagResource.getOne(tagService.create(tagDto));
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<TagDto>>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) String pageSize)
            throws ServiceValidationException, ServiceSearchException, ControllerException {
        return tagResource.getAll(tagService.findAll(page, pageSize), page, pageSize);
    }

    @GetMapping("/get/tag")
    @ResponseStatus(HttpStatus.OK)
    @Description("For lookups, id takes precedence over multiple passed parameters.")
    public Resource<TagDto> getOne(@RequestParam(value = "id", required = false) String id,
                                                @RequestParam(value = "name", required = false) String name)
            throws ControllerException, ServiceSearchException, ServiceValidationException {
        controllerValidator.validateParameters(id, name);
        Resource<TagDto> resource = null;
        if (StringUtils.isNotBlank(id)) {
            resource = tagResource.getOne(tagService.findById(id));
        } else if (StringUtils.isNotBlank(name)) {
            resource = tagResource.getOne(tagService.findByName(name));
        }
        return resource;
    }

    @GetMapping("/get/tag_most_popular")
    @ResponseStatus(HttpStatus.OK)
    public Resource<TagDto> getMostPopular()
            throws ServiceSearchException, ServiceValidationException, ControllerException {
        return tagResource.getOne(tagService.findMostPopular());
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<TagDto> delete(@PathVariable String id)
            throws ServiceValidationException, ServiceSearchException, ControllerException {
        return tagResource.delete(tagService.deleteById(id));
    }
}