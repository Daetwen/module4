package com.epam.esm.util;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {

    public TagDto convertTagToTagDto(Tag tag) {
        return TagDto.builder().id(tag.getId()).name(tag.getName()).build();
    }

    public Tag convertTagDtoToTag(TagDto tagDto) {
        return Tag.builder().id(tagDto.getId()).name(tagDto.getName()).build();
    }
}
