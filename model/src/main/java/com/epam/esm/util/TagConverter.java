package com.epam.esm.util;

import com.epam.esm.builder.TagBuilder;
import com.epam.esm.builder.TagDtoBuilder;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {

    public TagDto convertTagToTagDto(Tag tag) {
        TagDtoBuilder tagDtoBuilder = new TagDtoBuilder();
        tagDtoBuilder.setId(tag.getId());
        tagDtoBuilder.setName(tag.getName());
        return tagDtoBuilder.build();
    }

    public Tag convertTagDtoToTag(TagDto tagDto) {
        TagBuilder tagBuilder = new TagBuilder();
        tagBuilder.setId(tagDto.getId());
        tagBuilder.setName(tagDto.getName());
        return tagBuilder.build();
    }
}
