package com.famelive.api.enums

import com.famelive.common.enums.usermanagement.SocialAccount

public enum ApiSocialAccount {

    FACEBOOK("Facebook",SocialAccount.FACEBOOK),
    G_PLUS("Google Plus",SocialAccount.G_PLUS),
    TWITTER("Twitter",SocialAccount.TWITTER)

    String value;
    SocialAccount socialAccount

    ApiSocialAccount(String value,SocialAccount socialAccount) {
        this.value = value;
        this.socialAccount=socialAccount
    }

}
