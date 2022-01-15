package com.epam.esm.hateoas;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface Order resource.
 */
public interface OrderResource {

    /**
     * Hateoas for getOne function in order controller.
     *
     * @param orderDto the order dto
     * @return result hateoas for order
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<OrderDto> getOne(OrderDto orderDto)
            throws ServiceValidationException, ServiceSearchException;

    /**
     * Hateoas for getAll function in order controller.
     *
     * @param orderDtoList the order dto list
     * @param page         the current page
     * @param pageSize     the page size
     * @return result hateoas for list of orders
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    Resource<List<Resource<OrderDto>>> getAll(List<OrderDto> orderDtoList, String page, String pageSize)
            throws ServiceValidationException, ServiceSearchException;
}
