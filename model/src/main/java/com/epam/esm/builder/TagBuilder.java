package com.epam.esm.builder;

import com.epam.esm.entity.Tag;

public class TagBuilder {

    private Long id;
    private String name;

    public Tag build() {
        Tag tag = new Tag(id, name);
        this.id = null;
        this.name = null;
        return tag;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
