package com.famelive.admin.dto

import com.famelive.admin.dto.usermanagement.AdminFollowDto
import com.famelive.admin.dto.usermanagement.AdminLinkedSocialAccount
import com.famelive.common.dto.usermanagement.FollowDto
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.user.LinkedSocialAccount
import com.famelive.common.user.User

class AdminUserProfileDto extends AdminResponseDto {
    Long id
    String fameId
    String username
    String email
    String fameName
    String mobile
    String imageName
    Date dateCreated
    boolean accountLocked
    String registrationType
    List<AdminLinkedSocialAccount> socialAccounts
    List<AdminFollowDto> followers
    List<AdminFollowDto> followings

    AdminUserProfileDto() {}

    AdminUserProfileDto(UserProfileDto profileDto) {
        this.id = profileDto?.id
        this.username = profileDto?.username
        this.fameId = profileDto?.fameId
        this.email = profileDto?.email
        this.fameName = profileDto?.fameName
        this.mobile = profileDto?.mobile
        this.imageName = profileDto?.imageName
        this.dateCreated = profileDto?.dateCreated
        this.registrationType = profileDto?.registrationType?.value
        this.accountLocked = profileDto.accountLocked
        this.socialAccounts = populateLinkedSocialAccountList(profileDto?.linkedSocialAccounts)
        this.followers = populateFollowDto(profileDto?.followers)
        this.followings = populateFollowDto(profileDto?.followings)
    }

    static AdminUserProfileDto createAdminResponseDto(UserProfileDto profileDto) {
        return new AdminUserProfileDto(profileDto)
    }

    static List<AdminLinkedSocialAccount> populateLinkedSocialAccountList(List<LinkedSocialAccount> linkedSocialAccounts) {
        List<AdminLinkedSocialAccount> apiLinkedSocialAccountList = []
        if (linkedSocialAccounts) {
            linkedSocialAccounts.each { LinkedSocialAccount socialAccount ->
                apiLinkedSocialAccountList << AdminLinkedSocialAccount.createApiResponseDto(socialAccount)
            }
        }
        return apiLinkedSocialAccountList
    }

    static List<AdminFollowDto> populateFollowDto(List<FollowDto> userList) {
        List<AdminFollowDto> adminFollowDtos = []
        userList?.each { FollowDto followDto ->
            adminFollowDtos << AdminFollowDto.createCommonResponseDto(followDto)
        }
        return adminFollowDtos
    }
}
