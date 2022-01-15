package com.epam.esm.util;

import com.epam.esm.builder.OrderBuilder;
import com.epam.esm.builder.OrderDtoBuilder;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    private final UserConverter userConverter;
    private final CertificateConverter certificateConverter;

    @Autowired
    public OrderConverter(UserConverter userConverter, CertificateConverter certificateConverter) {
        this.userConverter = userConverter;
        this.certificateConverter = certificateConverter;
    }

    public OrderDto convertOrderToOrderDto(Order order) {
        OrderDtoBuilder orderDtoBuilder = new OrderDtoBuilder();
        orderDtoBuilder.setId(order.getId());
        orderDtoBuilder.setPrice(order.getPrice());
        orderDtoBuilder.setCreateDate(order.getCreateDate());
        orderDtoBuilder.setCertificate(
                certificateConverter.convertCertificateToCertificateDto(order.getCertificate()));
        orderDtoBuilder.setUser(userConverter.convertUserToUserDto(order.getUser()));
        return orderDtoBuilder.build();
    }

    public Order convertOrderDtoToOrder(OrderDto orderDto) {
        OrderBuilder orderBuilder = new OrderBuilder();
        orderBuilder.setId(orderDto.getId());
        orderBuilder.setPrice(orderDto.getPrice());
        orderBuilder.setCreateDate(orderDto.getCreateDate());
        orderBuilder.setCertificate(
                certificateConverter.convertCertificateDtoToCertificate(orderDto.getCertificate()));
        orderBuilder.setUser(userConverter.convertUserDtoToUser(orderDto.getUser()));
        return orderBuilder.build();
    }
}
