package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.OrderResource;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.service.OrderService;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE;
import static com.epam.esm.constant.PaginationParameter.DEFAULT_PAGE_SIZE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderResource orderResource;

    @Autowired
    public OrderController(OrderService orderService, OrderResource orderResource) {
        this.orderService = orderService;
        this.orderResource = orderResource;
    }

    @RequestMapping(value="/create",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<OrderDto> create(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "certificateId") String certificateId)
            throws ServiceValidationException, ServiceSearchException {
        return orderResource.getOne(orderService.create(userId, certificateId));
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<OrderDto>>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        return orderResource.getAll(orderService.findAll(page, pageSize), page, pageSize);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<OrderDto> getOne(@PathVariable String id)
            throws ServiceSearchException, ServiceValidationException {
        return orderResource.getOne(orderService.findById(id));
    }

    @GetMapping("/get/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<OrderDto>>> getByUserId (
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) String pageSize,
            @PathVariable String id)
            throws ServiceValidationException, ServiceSearchException {
        return orderResource.getAll(orderService.findByUserId(id, page, pageSize), page, pageSize);
    }
}
