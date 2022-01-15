package com.epam.esm.validator;

import com.epam.esm.constant.LanguagePath;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.util.LocaleManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerValidator {

    private final LocaleManager localeManager;

    @Autowired
    public ControllerValidator(LocaleManager localeManager) {
        this.localeManager = localeManager;
    }

    public void validateParameters(String id, String name) throws ControllerException {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(name)) {
            throw new ControllerException(
                    localeManager.getLocalizedMessage(LanguagePath.PARAMETERS_SEARCH_TAG_ERROR));
        }
    }
}
