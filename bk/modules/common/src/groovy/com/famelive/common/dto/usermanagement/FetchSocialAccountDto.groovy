package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.LinkedSocialAccount
import com.famelive.common.user.User

class FetchSocialAccountDto extends ResponseDto {

    List<LinkedSocialAccount> linkedSocialAccounts

    FetchSocialAccountDto() {}

    FetchSocialAccountDto(User user) {
        this.linkedSocialAccounts = user?.fetchActiveSocialAccount()
    }


    static FetchSocialAccountDto createCommonResponseDto(User user) {
        return new FetchSocialAccountDto(user)
    }

}
