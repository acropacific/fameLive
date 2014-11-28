package com.famelive.admin.enums

import com.famelive.common.enums.usermanagement.UserRegistrationType

public enum AdminUserRegistrationType {

    ALL("All", [UserRegistrationType.FACEBOOK, UserRegistrationType.G_PLUS, UserRegistrationType.MANUAL]),
    FACEBOOK("Facebook", [UserRegistrationType.FACEBOOK]),
    G_PLUS("Google Plus", [UserRegistrationType.G_PLUS]),
    MANUAL("Manual", [UserRegistrationType.MANUAL])

    String value
    List<UserRegistrationType> registrationTypes

    AdminUserRegistrationType(String value, List<UserRegistrationType> registrationTypes) {
        this.value = value;
        this.registrationTypes = registrationTypes
    }

}
