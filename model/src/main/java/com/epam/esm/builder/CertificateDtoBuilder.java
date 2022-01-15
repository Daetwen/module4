package com.epam.esm.builder;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class CertificateDtoBuilder {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private OffsetDateTime createDate;
    private OffsetDateTime lastUpdateDate;
    private List<TagDto> tags;

    public CertificateDto build() {
        CertificateDto certificateDto = new CertificateDto(id, name, description, price,
                duration, createDate, lastUpdateDate, tags);
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.createDate = null;
        this.lastUpdateDate = null;
        this.tags = null;
        return certificateDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(OffsetDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
