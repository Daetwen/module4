package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.CertificateResource;
import com.epam.esm.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateResourceImpl implements CertificateResource {

    private static final String CREATE_CERTIFICATE = "create";
    private static final String GET_THIS_CERTIFICATE = "getThis";
    private static final String GET_ALL_CERTIFICATES = "getAll";
    private static final String UPDATE_CERTIFICATE = "update";
    private static final String DELETE_CERTIFICATE = "delete";

    @Override
    public Resource<CertificateDto> getOne(CertificateDto certificateDto)
            throws ServiceValidationException, ServiceSearchException {
        return addCommonRefsForOne(certificateDto, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @Override
    public Resource<List<Resource<CertificateDto>>> getAll(List<CertificateDto> certificateDtoList,
                                                           String page,
                                                           String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        Resource<List<Resource<CertificateDto>>> resultResource =
                addCommonRefsForCollection(certificateDtoList, page, pageSize);
        resultResource.add(linkTo(methodOn(CertificateController.class)
                .getAll(page, pageSize)).withSelfRel());
        return resultResource;
    }

    @Override
    public Resource<List<Resource<CertificateDto>>> getByParameters(List<CertificateDto> certificateDtoList,
                                                                    List<String> tagNames,
                                                                    String part,
                                                                    String sort,
                                                                    String page,
                                                                    String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        Resource<List<Resource<CertificateDto>>> resultResource =
                addCommonRefsForCollection(certificateDtoList, page, pageSize);
        resultResource.add(linkTo(methodOn(CertificateController.class)
                .getAll(page, pageSize)).withRel(GET_ALL_CERTIFICATES));
        resultResource.add(linkTo(methodOn(CertificateController.class)
                .getSomeByParameters(tagNames, part, sort,page, pageSize)).withSelfRel());
        return resultResource;
    }

    public Resource<CertificateDto> delete(CertificateDto certificateDto)
            throws ServiceValidationException, ServiceSearchException {
        Resource<CertificateDto> certificateDtoResource = new Resource<>(certificateDto);
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .create(certificateDto)).withRel(CREATE_CERTIFICATE));
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)).withRel(GET_ALL_CERTIFICATES));
        return certificateDtoResource;
    }

    private Resource<CertificateDto> addCommonRefsForOne(CertificateDto certificateDto, String page, String pageSize)
            throws ServiceSearchException, ServiceValidationException {
        Resource<CertificateDto> certificateDtoResource = new Resource<>(certificateDto);
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .create(certificateDto)).withRel(CREATE_CERTIFICATE));
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .getOne(certificateDto.getId().toString())).withRel(GET_THIS_CERTIFICATE));
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .getAll(page, pageSize)).withRel(GET_ALL_CERTIFICATES));
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .update(certificateDto)).withRel(UPDATE_CERTIFICATE));
        certificateDtoResource.add(linkTo(methodOn(CertificateController.class)
                .delete(certificateDto.getId().toString())).withRel(DELETE_CERTIFICATE));
        return certificateDtoResource;
    }

    private Resource<List<Resource<CertificateDto>>> addCommonRefsForCollection(List<CertificateDto> certificateDtoList,
                                                                                String page,
                                                                                String pageSize)
            throws ServiceSearchException, ServiceValidationException {
        List<Resource<CertificateDto>> certificateDtoResourceList = new ArrayList<>();
        for (CertificateDto certificateDto : certificateDtoList) {
            certificateDtoResourceList.add(addCommonRefsForOne(certificateDto, page, pageSize));
        }
        return new Resource<>(certificateDtoResourceList);
    }
}
