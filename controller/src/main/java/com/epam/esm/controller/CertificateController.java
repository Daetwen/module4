package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.CertificateResource;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.service.CertificateService;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;

import com.epam.esm.util.ParameterName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private static final String DELIMITER = "#";
    private final CertificateService certificateService;
    private final CertificateResource certificateResource;

    @Autowired
    public CertificateController(CertificateService certificateService, CertificateResource certificateResource) {
        this.certificateService = certificateService;
        this.certificateResource = certificateResource;
    }

    @RequestMapping(value="/create",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<CertificateDto> create(@RequestBody CertificateDto certificateDto)
            throws ServiceSearchException, ServiceValidationException {
        return certificateResource.getOne(certificateService.create(certificateDto));
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<CertificateDto>>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        return certificateResource.getAll(certificateService.findAll(page, pageSize), page, pageSize);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<CertificateDto> getOne(@PathVariable String id)
            throws ServiceSearchException, ServiceValidationException {
        return certificateResource.getOne(certificateService.findById(id));
    }

    @GetMapping("/get/certificate_parameters")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<CertificateDto>>> getSomeByParameters(
            @RequestParam(value = "tagName", required = false) List<String> tagNames,
            @RequestParam(value = "part", required = false) String part,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        Map<String, String> parameters = new HashMap<>();
        collectParametersForList(parameters, ParameterName.TAG_NAME, tagNames);
        collectParameters(parameters, ParameterName.NAME_OR_DESC_PART, part);
        collectParameters(parameters, ParameterName.SORT, sort);
        return certificateResource.getByParameters(
                certificateService.findByParameters(parameters, page, pageSize),
                tagNames, part, sort, page, pageSize);
    }

    @RequestMapping(value="/update", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Resource<CertificateDto> update(@RequestBody CertificateDto certificateDto)
            throws ServiceSearchException, ServiceValidationException {
        return certificateResource.getOne(certificateService.update(certificateDto));
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<CertificateDto> delete(@PathVariable String id)
            throws ServiceValidationException, ServiceSearchException {
        return certificateResource.delete(certificateService.deleteById(id));
    }

    private void collectParameters(Map<String, String> parameters,
                                   ParameterName parameterName,
                                   String parameter) {
        if (StringUtils.isNotBlank(parameter)) {
            parameters.put(parameterName.name(), parameter);
        }
    }

    private void collectParametersForList(Map<String, String> parameters,
                                   ParameterName parameterName,
                                   List<String> parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            for (int i = 0; i < parameter.size(); i++) {
                parameters.put(parameterName.name() + DELIMITER + i, parameter.get(i));
            }
        }
    }
}
