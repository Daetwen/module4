package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.hateoas.OrderResource;
import com.epam.esm.hateoas.Resource;
import com.epam.esm.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.constant.PaginationParameter.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "${angular.origin.url}")
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
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE_ORDERS) String pageSize)
            throws ServiceValidationException, ServiceSearchException {
        return orderResource.getAll(orderService.findAll(page, pageSize), page, pageSize);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<OrderDto> getOne(@PathVariable String id)
            throws ServiceSearchException, ServiceValidationException {
        return orderResource.getOne(orderService.findById(id));
    }

    @GetMapping("/get/personal")
    @ResponseStatus(HttpStatus.OK)
    public Resource<OrderDto> getOneForUser(
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "userId") String userId)
            throws ServiceSearchException, ServiceValidationException {
        return orderResource.getOne(orderService.findByIdAndUserId(orderId, userId));
    }

    @GetMapping("/get/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<List<Resource<OrderDto>>> getByUserId (
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE_ORDERS) String pageSize,
            @PathVariable String id)
            throws ServiceValidationException, ServiceSearchException {
        return orderResource.getAll(orderService.findByUserId(id, page, pageSize), page, pageSize);
    }
}
