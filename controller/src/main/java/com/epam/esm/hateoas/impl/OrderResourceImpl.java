package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.OrderResource;
import com.epam.esm.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderResourceImpl implements OrderResource {

    private static final String CREATE_ORDER = "create";
    private static final String GET_THIS_ORDERS = "getThis";
    private static final String GET_ALL_ORDERS = "getAll";
    private static final String GET_ORDER_BY_USER_ID = "getByUserId";

    @Override
    public Resource<OrderDto> getOne(OrderDto orderDto)
            throws ServiceValidationException, ServiceSearchException {
        return addCommonRefsForOne(orderDto, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @Override
    public Resource<List<Resource<OrderDto>>> getAll(List<OrderDto> orderDtoList, String page, String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        List<Resource<OrderDto>> orderDtoResourceList = new ArrayList<>();
        for (OrderDto orderDto : orderDtoList) {
            orderDtoResourceList.add(addCommonRefsForOne(orderDto, page, pageSize));
        }
        Resource<List<Resource<OrderDto>>> resultResource = new Resource<>(orderDtoResourceList);
        resultResource.add(linkTo(methodOn(OrderController.class)
                .getAll(page, pageSize)).withSelfRel());
        return resultResource;
    }

    private Resource<OrderDto> addCommonRefsForOne(OrderDto orderDto, String page, String pageSize)
            throws ServiceSearchException, ServiceValidationException {
        Resource<OrderDto> orderDtoResource = new Resource<>(orderDto);
        orderDtoResource.add(linkTo(methodOn(OrderController.class)
                .create(orderDto.getUser().getId().toString(),
                        orderDto.getCertificate().getId().toString()))
                .withRel(CREATE_ORDER));
        orderDtoResource.add(linkTo(methodOn(OrderController.class)
                .getOne(orderDto.getId().toString())).withRel(GET_THIS_ORDERS));
        orderDtoResource.add(linkTo(methodOn(OrderController.class)
                .getAll(page, pageSize)).withRel(GET_ALL_ORDERS));
        orderDtoResource.add(linkTo(methodOn(OrderController.class)
                .getByUserId(page, pageSize,
                        orderDto.getUser().getId().toString())).withRel(GET_ORDER_BY_USER_ID));
        return orderDtoResource;
    }
}
