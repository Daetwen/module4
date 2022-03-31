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
import com.epam.esm.util.Verifier;
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
    private final Verifier verifier;
    private final TagDao tagDao;
    private final LocaleManager localeManager;

    @Autowired
    public TagServiceImpl(TagDao tagDao, Validator validator, Verifier verifier,
                          TagConverter tagConverter, LocaleManager localeManager) {
        this.tagDao = tagDao;
        this.validator = validator;
        this.verifier = verifier;
        this.tagConverter = tagConverter;
        this.localeManager = localeManager;
    }

    @Override
    public TagDto create(TagDto tagDto) throws ServiceSearchException {
        Tag createdTag = null;
        if (verifier.isValidTag(tagDto)) {
            createdTag = tagDao.save(tagConverter.convertTagDtoToTag(tagDto));
        }
        validator.validateTag(createdTag);
        return tagConverter.convertTagToTagDto(createdTag);
    }

    @Override
    public TagDto findById(String id) throws ServiceSearchException, ServiceValidationException {
        validator.validateId(id);
        Optional<Tag> result = tagDao.findById(Long.parseLong(id));
        validator.validateTag(result);
        return tagConverter.convertTagToTagDto(result.get());
    }

    @Override
    public TagDto findByName(String name)
            throws ServiceSearchException, ServiceValidationException {
        validator.validateName(name);
        Optional<Tag> result = tagDao.findByName(name);
        validator.validateTag(result);
        return tagConverter.convertTagToTagDto(result.get());
    }

    @Override
    public List<TagDto> findAll(String page, String pageSize)
            throws ServiceValidationException {
        validator.validatePage(page);
        validator.validatePage(pageSize);
        List<TagDto> tagDtoList = new ArrayList<>();
        if (Integer.parseInt(page) <= getCountOfPages(pageSize)) {
            for (Tag element : tagDao.findAllByOrderByIdDesc(
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(pageSize)))) {
                tagDtoList.add(tagConverter.convertTagToTagDto(element));
            }
        }
        return tagDtoList;
    }

    @Override
    public TagDto findMostPopular() throws ServiceSearchException {
        Tag result = tagDao.findMostPopular();
        validator.validateTag(result);
        return tagConverter.convertTagToTagDto(result);
    }

    @Override
    public TagDto deleteById(String id) throws ServiceValidationException, ServiceSearchException {
        validator.validateId(id);
        Optional<Tag> result = tagDao.findById(Long.parseLong(id));
        validator.validateTag(result);
        tagDao.deleteById(Long.parseLong(id));
        return tagConverter.convertTagToTagDto(result.get());
    }

    private long getCountOfPages(String pageSize) {
        long countOfRecords = tagDao.count();
        int size = Integer.parseInt(pageSize);
        long countPages = countOfRecords % size == 0 ? countOfRecords / size : countOfRecords / size + 1;
        return countPages == 0 ? 1 : countPages;
    }
}
