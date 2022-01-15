package com.epam.esm.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Audited
@Table(name = "gift_certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "create_date", nullable = false)
    private OffsetDateTime createDate;

    @Column(name = "last_update_date", nullable = false)
    private OffsetDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificates_has_tags",
            joinColumns = @JoinColumn(name = "gift_certificates_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private List<Tag> tags = new ArrayList<>();

    public Certificate() {}

    public Certificate(Long id,
                       String name,
                       String description,
                       BigDecimal price,
                       int duration,
                       OffsetDateTime createDate,
                       OffsetDateTime lastUpdateDate,
                       List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            for (Tag element : tags) {
                Tag tag = new Tag(element);
                this.tags.add(tag);
            }
        }
    }

    public Certificate(Certificate certificate) {
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.description = certificate.getDescription();
        this.price = certificate.getPrice();
        this.duration = certificate.getDuration();
        this.createDate = certificate.getCreateDate();
        this.lastUpdateDate = certificate.getLastUpdateDate();
        this.tags = new ArrayList<>();
        if (certificate.getTags() != null && !certificate.getTags().isEmpty()) {
            for (Tag element : certificate.getTags()) {
                Tag tag = new Tag(element);
                this.tags.add(tag);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public OffsetDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(OffsetDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        List<Tag> resultTags = new ArrayList<>();
        if (this.tags != null) {
            for (Tag value : this.tags) {
                Tag tag = new Tag(value);
                resultTags.add(tag);
            }
        }
        return resultTags;
    }

    public void setTags(List<Tag> tags) {
        if (tags != null) {
            this.tags = new ArrayList<>();
            for (Tag element : tags) {
                Tag tag = new Tag(element);
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
        Certificate that = (Certificate) obj;
        return id.equals(that.id)
                && name.equals(that.name)
                && Objects.equals(description, that.description)
                && price.equals(that.price)
                && duration == that.duration
                && createDate.equals(that.createDate)
                && lastUpdateDate.truncatedTo( ChronoUnit.SECONDS )
                .equals(that.lastUpdateDate.truncatedTo( ChronoUnit.SECONDS ))
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
        result = result * prime + duration;
        result = result * prime + (createDate != null ? createDate.hashCode() : 0);
        result = result * prime + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = result * prime + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GiftCertificate{ ")
                .append("id = '").append(id).append('\'')
                .append(", name = '").append(name).append('\'')
                .append(", description = '").append(description).append('\'')
                .append(", price = '").append(price).append('\'')
                .append(", duration = '").append(duration).append('\'')
                .append(", createDate = '").append(createDate).append('\'')
                .append(", lastUpdateDate = '").append(lastUpdateDate).append('\'');
        stringBuilder.append(", tags = [");
        for(Tag tag : this.tags) {
            stringBuilder.append(tag).append(", ");
        }
        stringBuilder.append("] }\n");
        return stringBuilder.toString();
    }
}
