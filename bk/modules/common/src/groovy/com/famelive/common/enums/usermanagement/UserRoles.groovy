package com.famelive.common.enums.usermanagement

public enum UserRoles {
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    EVENT_ORGANIZER("ROLE_EVENT_ORGANIZER"),
    PERFORMER("ROLE_PERFORMER"),
    WATCHER("ROLE_WATCHER"),
    GUEST("ROLE_GUEST"),
    QC("ROLE_QC"),
    ANONYMOUS("ROLE_ANONYMOUS"),
    FACEBOOK_USER("ROLE_FACEBOOK_USER"),
    TWITTER_USER("ROLE_TWITTER_USER"),
    GOOGLE_USER("ROLE_GOOGLE_USER"),
    USER("ROLE_USER"),
    STREAMING_ADMIN("STREAMING_ADMIN");

    String value;

    UserRoles(String value) {
        this.value = value;
    }
}
