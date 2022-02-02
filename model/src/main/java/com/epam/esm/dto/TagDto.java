package com.epam.esm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TagDto {

    private Long id;
    private String name;

    public TagDto() {}

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDto(TagDto tag) {
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
        TagDto tag = (TagDto) obj;
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
        stringBuilder.append("TagDto { ")
                .append("id = '").append(id).append('\'')
                .append(", name = '").append(name).append('\'')
                .append(" }\n");
        return stringBuilder.toString();
    }
}
