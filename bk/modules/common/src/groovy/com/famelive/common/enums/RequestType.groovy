package com.famelive.common.enums

/**
 * Created by anil on 28/10/14.
 */
public enum RequestType {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete")

    String value

    RequestType(String value) {
        this.value = value
    }

}