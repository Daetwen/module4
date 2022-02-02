package com.epam.esm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
public class CertificateDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private OffsetDateTime createDate;
    private OffsetDateTime lastUpdateDate;
    private List<TagDto> tags;

    public CertificateDto() {}

    public CertificateDto(Long id,
                       String name,
                       String description,
                       BigDecimal price,
                       int duration,
                       OffsetDateTime createDate,
                       OffsetDateTime lastUpdateDate,
                       List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            for (TagDto tagDto : tags) {
                TagDto tag = new TagDto(tagDto);
                this.tags.add(tag);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CertificateDto that = (CertificateDto) obj;
        return id.equals(that.id)
                && name.equals(that.name)
                && Objects.equals(description, that.description)
                && price.equals(that.price)
                && duration.equals(that.duration)
                && createDate.equals(that.createDate)
                && lastUpdateDate.equals(that.lastUpdateDate)
                && tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + (id != null ? id.hashCode() : 0);
        result = result * prime + (name != null ? name.hashCode() : 0);
        result = result * prime + (description != null ? description.hashCode() : 0);
        result = result * prime + (price != null ? price.hashCode() : 0);
        result = result * prime + (duration != null ? duration.hashCode() : 0);
        result = result * prime + (createDate != null ? createDate.hashCode() : 0);
        result = result * prime + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = result * prime + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CertificateDto { ")
                .append("id = '").append(id).append('\'')
                .append(", name = '").append(name).append('\'')
                .append(", description = '").append(description).append('\'')
                .append(", price = '").append(price).append('\'')
                .append(", duration = '").append(duration).append('\'')
                .append(", createDate = '").append(createDate).append('\'')
                .append(", lastUpdateDate = '").append(lastUpdateDate).append('\'');
        stringBuilder.append(", tags = [");
        for(TagDto tag : tags) {
            stringBuilder.append(tag).append(", ");
        }
        stringBuilder.append("] }\n");
        return stringBuilder.toString();
    }
}
