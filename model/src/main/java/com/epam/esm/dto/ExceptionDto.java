package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ExceptionDto {

    private String errorMessage;
    private long errorCode;

    public ExceptionDto() {}

    public ExceptionDto(String errorMessage, long errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExceptionDto that = (ExceptionDto) obj;
        return errorCode == that.errorCode
                && Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + (int) errorCode;
        result = result * prime + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n")
                .append("\terrorMessage: \"").append(errorMessage).append("\"\n")
                .append("\terrorCode: \"").append(errorCode).append("\",\n")
                .append("}\n");
        return stringBuilder.toString();
    }
}
