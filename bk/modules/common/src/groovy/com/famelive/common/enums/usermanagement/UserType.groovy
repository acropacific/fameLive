package com.famelive.common.enums.usermanagement

public enum UserType {

    PERFORMER("Performer", UserRoles.PERFORMER),
    VIEWER("Viewer", UserRoles.WATCHER)

    String value;
    UserRoles userRoles

    UserType(String value, UserRoles userRoles) {
        this.value = value;
        this.userRoles = userRoles
    }

}
