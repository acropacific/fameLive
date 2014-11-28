package com.famelive.api.enums

import com.famelive.common.enums.usermanagement.UserType

public enum ApiUserType {

    PERFORMER("Performer", UserType.PERFORMER),
    VIEWER("Viewer", UserType.VIEWER)

    String value;
    UserType userType

    ApiUserType(String value, UserType userType) {
        this.value = value
        this.userType = userType
    }

}
