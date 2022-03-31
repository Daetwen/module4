package com.epam.esm.security.handler;

import com.epam.esm.constant.ErrorCode;
import com.epam.esm.constant.LanguagePath;
import com.epam.esm.dto.ExceptionDto;
import com.epam.esm.util.LocaleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final String ENCODING = "UTF-8";
    private static final String RESPONSE_JSON = "application/json";

    @Autowired
    private LocaleManager localeManager;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        String errorMessage = localeManager.getLocalizedMessage(LanguagePath.ERROR_FORBIDDEN);
        ExceptionDto exceptionDto = new ExceptionDto(errorMessage, ErrorCode.FORBIDDEN_ERROR_CODE);
        response.setContentType(RESPONSE_JSON);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(String.valueOf(exceptionDto));
    }
}
