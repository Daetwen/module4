package com.epam.esm.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Builder
@Getter
@Setter
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "name", length = 45, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private final List<Certificate> certificates = new ArrayList<>();

    public Tag() {}

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tag tag = (Tag) obj;
        return id.equals(tag.id)
                && name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + (id != null ? id.hashCode() : 0);
        result = result * prime + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tag{ ")
                .append("id = '").append(id).append('\'')
                .append(", name = '").append(name).append('\'')
                .append(" }\n");
        return stringBuilder.toString();
    }
}
