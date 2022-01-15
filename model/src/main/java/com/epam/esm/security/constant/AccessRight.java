package com.epam.esm.security.constant;

public enum AccessRight {
    ALL_REGISTER("/users/register"),
    ALL_LOGIN("/users/login"),

    CERTIFICATE_CREATE("/certificates/create"),
    CERTIFICATE_GET("/certificates/get/*"),
    CERTIFICATE_GET_ALL("/certificates/get"),
    CERTIFICATE_GET_BY_PARAMETERS("/certificates/get/certificate_parameter"),
    CERTIFICATE_UPDATE("/certificates/update"),
    CERTIFICATE_DELETE("/certificates/delete/*"),

    ORDER_CREATE("/orders/create"),
    ORDER_GET("/orders/get/*"),
    ORDER_GET_ALL("/orders/get"),
    ORDER_GET_BY_USER_ID("/orders/get/user/*"),

    TAG_CREATE("tags/create"),
    TAG_GET("tags/get/tag"),
    TAG_GET_ALL("tags/get"),
    TAG_GET_MOST_POPULAR("/get/tag_most_popular"),
    TAG_DELETE("tags/delete/*"),

    USER_GET("/users/get/*"),
    USER_GET_ALL("/users/get");


    private final String urn;

    AccessRight(String accessRight) {
        this.urn = accessRight;
    }

    @Override
    public String toString() {
        return urn;
    }
}