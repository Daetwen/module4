package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderDto {

    private Long id;
    private BigDecimal price;
    private OffsetDateTime createDate;
    private UserDto user;
    private CertificateDto certificate;

    public OrderDto() {}

    public OrderDto(Long id, BigDecimal price, OffsetDateTime createDate, UserDto user, CertificateDto certificate) {
        this.id = id;
        this.price = price;
        this.createDate = createDate;
        this.user = user;
        this.certificate = certificate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateDto certificate) {
        this.certificate = certificate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OrderDto orderDto = (OrderDto) obj;
        return id.equals(orderDto.id)
                && price.equals(orderDto.price)
                && createDate.equals(orderDto.createDate)
                && user.equals(orderDto.user)
                && certificate.equals(orderDto.certificate);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + (id != null ? id.hashCode() : 0);
        result = result * prime + (price != null ? price.hashCode() : 0);
        result = result * prime + (createDate != null ? createDate.hashCode() : 0);
        result = result * prime + (user != null ? user.hashCode() : 0);
        result = result * prime + (certificate != null ? certificate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OrderDto { ")
                .append("id = '").append(id).append('\'')
                .append(", price = '").append(price).append('\'')
                .append(", createDate = '").append(createDate).append('\'')
                .append(", user = '").append(user).append('\'')
                .append(", certificate = '").append(certificate).append('\'')
                .append(" }\n");
        return stringBuilder.toString();
    }
}
