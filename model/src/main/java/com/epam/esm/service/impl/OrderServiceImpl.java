package com.epam.esm.service.impl;

import com.epam.esm.constant.LanguagePath;
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
    private final LocaleManager localeManager;
    private final UserConverter userConverter;
    private final CertificateConverter certificateConverter;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, CertificateDao certificateDao, UserDao userDao,
                            Validator validator, LocaleManager localeManager, UserConverter userConverter,
                            CertificateConverter certificateConverter, OrderConverter orderConverter) {
        this.orderDao = orderDao;
        this.certificateDao = certificateDao;
        this.userDao = userDao;
        this.validator = validator;
        this.localeManager = localeManager;
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
        if (validator.isUserExist(Long.parseLong(userId))
                && validator.isCertificateExist(Long.parseLong(certificateId))) {
            OrderDto orderDto = new OrderDto();
            orderDto.setCreateDate(OffsetDateTime.now());
            orderDto.setUser(userConverter.convertUserToUserDto(userDao.findById(Long.parseLong(userId)).get()));
            orderDto.setCertificate(certificateConverter.convertCertificateToCertificateDto(
                            certificateDao.findById(Long.parseLong(certificateId)).get()));
            orderDto.setPrice(certificateDao.findById(Long.parseLong(certificateId)).get().getPrice());
            order = orderDao.save(orderConverter.convertOrderDtoToOrder(orderDto));
        }
        return checkOrder(order);
    }

    @Override
    public OrderDto findById(String id) throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<Order> result = orderDao.findById(Long.parseLong(id));
        return checkOrder(result);
    }

    @Override
    public List<OrderDto> findAll(String page, String pageSize)
            throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<OrderDto> orderDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPages(pageSize)) {
            for (Order element : orderDao.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(pageSize)))) {
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
                    Integer.parseInt(page),
                    Integer.parseInt(pageSize)));
            for (Order element : resultOrders) {
                orderDtoList.add(orderConverter.convertOrderToOrderDto(element));
            }
        }
        return orderDtoList;
    }

    private OrderDto checkOrder(Optional<Order> order)
            throws ServiceSearchException {
        if (order.isPresent()) {
            return orderConverter.convertOrderToOrderDto(order.get());
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    private OrderDto checkOrder(Order order)
            throws ServiceSearchException {
        if (!Objects.isNull(order)) {
            return orderConverter.convertOrderToOrderDto(order);
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
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
