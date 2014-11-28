package com.famelive.api.dto.usermanagement

import com.famelive.common.user.LinkedSocialAccount

class ApiLinkedSocialAccount {
    String socialAccount
    String token

    ApiLinkedSocialAccount() {}

    ApiLinkedSocialAccount(LinkedSocialAccount linkedSocialAccount) {
        this.socialAccount = linkedSocialAccount.socialAccount
        this.token = linkedSocialAccount.token ?: ""
    }

    static ApiLinkedSocialAccount createApiResponseDto(LinkedSocialAccount linkedSocialAccount) {
        return new ApiLinkedSocialAccount(linkedSocialAccount)
    }
}
