package com.epam.esm.builder;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderDtoBuilder {

    private Long id;
    private BigDecimal price;
    private OffsetDateTime createDate;
    private UserDto user;
    private CertificateDto certificate;

    public OrderDto build() {
        OrderDto orderDto = new OrderDto(id, price, createDate, user, certificate);
        this.id = null;
        this.price = null;
        this.createDate = null;
        this.user = null;
        this.certificate = null;
        return orderDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setCertificate(CertificateDto certificate) {
        this.certificate = certificate;
    }
}
