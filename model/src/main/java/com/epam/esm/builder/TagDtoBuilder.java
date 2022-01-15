package com.epam.esm.builder;

import com.epam.esm.dto.TagDto;

public class TagDtoBuilder {

    private Long id;
    private String name;

    public TagDto build() {
        TagDto tagDto = new TagDto(id, name);
        this.id = null;
        this.name = null;
        return tagDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
