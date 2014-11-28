package com.famelive.common.enums.usermanagement

public enum UserRegistrationType {

    FACEBOOK("Facebook", SocialAccount.FACEBOOK),
    G_PLUS("Google Plus", SocialAccount.G_PLUS),
    MANUAL("Manual", null)

    String value;
    SocialAccount socialAccount

    UserRegistrationType(String value, SocialAccount socialAccount) {
        this.value = value;
        this.socialAccount = socialAccount
    }

}
