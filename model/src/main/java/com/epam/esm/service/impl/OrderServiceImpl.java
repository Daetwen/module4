package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CertificateDao certificateDao;
    private final UserDao userDao;
    private final Validator validator;
    private final Verifier verifier;
    private final UserConverter userConverter;
    private final CertificateConverter certificateConverter;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, CertificateDao certificateDao, UserDao userDao,
                            Validator validator, Verifier verifier, UserConverter userConverter,
                            CertificateConverter certificateConverter, OrderConverter orderConverter) {
        this.orderDao = orderDao;
        this.certificateDao = certificateDao;
        this.userDao = userDao;
        this.validator = validator;
        this.verifier = verifier;
        this.userConverter = userConverter;
        this.certificateConverter = certificateConverter;
        this.orderConverter = orderConverter;
    }

    @Override
    public OrderDto create(String userId, String certificateId)
            throws ServiceValidationException, ServiceSearchException {
        Order order = null;
        validator.validateId(userId);
        validator.validateId(certificateId);
        if (verifier.isUserExist(Long.parseLong(userId))
                && verifier.isCertificateExist(Long.parseLong(certificateId))) {
            OrderDto orderDto = new OrderDto();
            orderDto.setCreateDate(OffsetDateTime.now());
            orderDto.setUser(userConverter.convertUserToUserDto(userDao.findById(Long.parseLong(userId)).get()));
            orderDto.setCertificate(certificateConverter.convertCertificateToCertificateDto(
                            certificateDao.findById(Long.parseLong(certificateId)).get()));
            orderDto.setPrice(certificateDao.findById(Long.parseLong(certificateId)).get().getPrice());
            order = orderDao.save(orderConverter.convertOrderDtoToOrder(orderDto));
        }
        validator.validateOrder(order);
        return orderConverter.convertOrderToOrderDto(order);
    }

    @Override
    public OrderDto findById(String id)
            throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<Order> result = orderDao.findById(Long.parseLong(id));
        validator.validateOrder(result);
        return orderConverter.convertOrderToOrderDto(result.get());
    }

    @Override
    public OrderDto findByIdAndUserId(String orderId, String userId)
            throws ServiceSearchException, ServiceValidationException {
        validator.validateId(orderId);
        validator.validateId(userId);
        Optional<Order> result = orderDao.findByIdAndUserId(
                Long.parseLong(orderId), Long.parseLong(userId));
        validator.validateOrder(result);
        return orderConverter.convertOrderToOrderDto(result.get());
    }

    @Override
    public List<OrderDto> findAll(String page, String pageSize)
            throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<OrderDto> orderDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPages(pageSize)) {
            for (Order element : orderDao.findAll(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(pageSize)))) {
                orderDtoList.add(orderConverter.convertOrderToOrderDto(element));
            }
        }
        return orderDtoList;
    }

    @Override
    public List<OrderDto> findByUserId(String userId, String page, String pageSize)
            throws ServiceValidationException {
        validator.validateId(userId);
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<OrderDto> orderDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPagesOfUserOrders(userId, pageSize)) {
            Page<Order> resultOrders = orderDao.findByUserId(Long.parseLong(userId), PageRequest.of(
                    Integer.parseInt(page) - 1,
                    Integer.parseInt(pageSize)));
            for (Order element : resultOrders) {
                orderDtoList.add(orderConverter.convertOrderToOrderDto(element));
            }
        }
        return orderDtoList;
    }

    private long getCountOfPages(String pageSize) throws ServiceValidationException {
        validator.validatePage(pageSize);
        long countOfRecords = orderDao.count();
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }

    private long getCountOfPagesOfUserOrders(String id, String pageSize) {
        long countOfRecords = orderDao.countByUserId(Long.parseLong(id));
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }
}
