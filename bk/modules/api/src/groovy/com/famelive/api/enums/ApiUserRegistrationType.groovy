package com.famelive.api.enums

import com.famelive.common.enums.usermanagement.UserRegistrationType

public enum ApiUserRegistrationType {

    FACEBOOK("Facebook", UserRegistrationType.FACEBOOK),
    G_PLUS("Google Plus", UserRegistrationType.G_PLUS),
    MANUAL("Manual", UserRegistrationType.MANUAL)

    String value
    UserRegistrationType registrationType

    ApiUserRegistrationType(String value, UserRegistrationType registrationType) {
        this.value = value;
        this.registrationType = registrationType
    }

}
