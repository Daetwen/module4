package com.epam.esm.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class Resource<T> extends RepresentationModel<Resource<T>> {

    private final T content;

    @JsonCreator
    public Resource(@JsonProperty("content") T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
