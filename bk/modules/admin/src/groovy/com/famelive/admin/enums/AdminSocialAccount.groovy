package com.famelive.admin.enums

import com.famelive.common.enums.usermanagement.SocialAccount

public enum AdminSocialAccount {

    FACEBOOK("Facebook", [SocialAccount.FACEBOOK]),
    G_PLUS("Google Plus", [SocialAccount.FACEBOOK]),
    TWITTER("Twitter", [SocialAccount.FACEBOOK]),
    ALL("All", SocialAccount.values() as List)

    String value;
    List<SocialAccount> socialAccounts

    AdminSocialAccount(String value, List<SocialAccount> socialAccounts) {
        this.value = value;
        this.socialAccounts = socialAccounts
    }

}
