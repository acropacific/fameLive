package com.famelive.admin.enums

import com.famelive.common.enums.usermanagement.UserType

public enum AdminUserType {

    BOTH("Both", [UserType.PERFORMER, UserType.VIEWER]),
    PERFORMER("Performer", [UserType.PERFORMER]),
    VIEWER("Viewer", [UserType.VIEWER])

    String value;
    List<UserType> userTypes

    AdminUserType(String value, List<UserType> userTypes) {
        this.value = value
        this.userTypes = userTypes
    }

}
