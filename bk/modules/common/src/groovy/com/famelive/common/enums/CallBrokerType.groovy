package com.famelive.common.enums

/**
 * Created by anil on 17/10/14.
 */
public enum CallBrokerType {
    API_CALL_BROKER("apiCallBroker"),
    WOWZA_CALL_BROKER("wowzaCallBroker")

    String value

    CallBrokerType(String value) {
        this.value = value
    }
}