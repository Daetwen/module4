package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {

    /**
     * Create order.
     *
     * @param userId        the user id
     * @param certificateId the certificate id
     * @return the created order dto
     * @throws ServiceValidationException the service validation exception
     * @throws ServiceSearchException     the service search exception
     */
    OrderDto create(String userId, String certificateId) throws ServiceValidationException, ServiceSearchException;

    /**
     * Find order by id.
     *
     * @param id the id
     * @return the result order dto
     * @throws ServiceSearchException     the service search exception
     * @throws ServiceValidationException the service validation exception
     */
    OrderDto findById(String id) throws ServiceSearchException, ServiceValidationException;

    /**
     * Find all orders list.
     *
     * @param page     the current page
     * @param pageSize the page size
     * @return the list of all orders
     * @throws ServiceValidationException the service validation exception
     */
    List<OrderDto> findAll(String page, String pageSize) throws ServiceValidationException;

    /**
     * Find orders by user id.
     *
     * @param userId   the user id
     * @param page     the current page
     * @param pageSize the page size
     * @return the list of user orders
     * @throws ServiceValidationException the service validation exception
     */
    List<OrderDto> findByUserId(String userId, String page, String pageSize) throws ServiceValidationException;
}
