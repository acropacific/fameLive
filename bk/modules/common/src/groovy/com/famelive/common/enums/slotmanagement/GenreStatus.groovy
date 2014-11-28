package com.famelive.common.enums.slotmanagement

public enum GenreStatus {

    PUBLISHED("Published"),
    UN_PUBLISHED("Un published")

    String value;

    GenreStatus(String value) {
        this.value = value;
    }

}