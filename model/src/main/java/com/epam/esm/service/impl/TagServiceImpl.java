package com.epam.esm.service.impl;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceSearchException;
import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.LocaleManager;
import com.epam.esm.util.TagConverter;
import com.epam.esm.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagConverter tagConverter;
    private final Validator validator;
    private final TagDao tagDao;
    private final LocaleManager localeManager;

    @Autowired
    public TagServiceImpl(TagDao tagDao, Validator validator,
                          TagConverter tagConverter, LocaleManager localeManager) {
        this.tagDao = tagDao;
        this.validator = validator;
        this.tagConverter = tagConverter;
        this.localeManager = localeManager;
    }

    @Override
    public TagDto create(TagDto tagDto) throws ServiceSearchException {
        Tag createdTag = null;
        if (validator.isValidTag(tagDto)) {
            createdTag = tagDao.save(tagConverter.convertTagDtoToTag(tagDto));
        }
        return checkTag(createdTag);
    }

    @Override
    public TagDto findById(String id) throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<Tag> result = tagDao.findById(Long.parseLong(id));
        return checkTag(result);
    }

    @Override
    public TagDto findByName(String name)
            throws ServiceSearchException, ServiceValidationException {
        validator.validateName(name);
        Optional<Tag> result = tagDao.findByName(name);
        return checkTag(result);
    }

    @Override
    public List<TagDto> findAll(String page, String pageSize)
            throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<TagDto> tagDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPages(pageSize)) {
            for (Tag element : tagDao.findAll(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(pageSize)))) {
                tagDtoList.add(tagConverter.convertTagToTagDto(element));
            }
        }
        return tagDtoList;
    }

    @Override
    public TagDto findMostPopular() throws ServiceSearchException {
        Tag result = tagDao.findMostPopular();
        return checkTag(result);
    }

    @Override
    public TagDto deleteById(String id) throws ServiceValidationException, ServiceSearchException {
        validator.validateId(id);
        Optional<Tag> result = tagDao.findById(Long.parseLong(id));
        tagDao.deleteById(Long.parseLong(id));
        return checkTag(result);
    }

    private TagDto checkTag(Optional<Tag> tag) throws ServiceSearchException {
        if (tag.isPresent()) {
            return tagConverter.convertTagToTagDto(tag.get());
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    private TagDto checkTag(Tag tag) throws ServiceSearchException {
        if (!Objects.isNull(tag)) {
            return tagConverter.convertTagToTagDto(tag);
        } else {
            throw new ServiceSearchException(
                    localeManager.getLocalizedMessage(LanguagePath.ERROR_NOT_FOUND));
        }
    }

    private long getCountOfPages(String pageSize) {
        long countOfRecords = tagDao.count();
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }
}
