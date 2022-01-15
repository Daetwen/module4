package com.epam.esm.builder;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderBuilder {

    private Long id;
    private BigDecimal price;
    private OffsetDateTime createDate;
    private User user;
    private Certificate certificate;

    public Order build() {
        Order order = new Order(id, price, createDate, user, certificate);
        this.id = null;
        this.price = null;
        this.createDate = null;
        this.user = null;
        this.certificate = null;
        return order;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
}
