package com.epam.esm.dto;

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
