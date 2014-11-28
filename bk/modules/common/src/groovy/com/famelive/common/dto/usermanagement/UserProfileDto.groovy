package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.enums.usermanagement.UserType
import com.famelive.common.followermanagement.Follow
import com.famelive.common.user.LinkedSocialAccount
import com.famelive.common.user.User

class UserProfileDto extends ResponseDto {

    Long id
    String fameId
    String username
    String email
    String fameName
    String mobile
    String imageName
    Date dateCreated
    boolean accountLocked
    UserRegistrationType registrationType
    UserType type
    String imageFolder
    List<LinkedSocialAccount> linkedSocialAccounts
    Boolean isFollower
    Long totalFollowers
    List<FollowDto> followers
    List<FollowDto> followings

    UserProfileDto() {}

    UserProfileDto(User user) {
        this.id = user?.id
        this.fameId = user?.fameId
        this.username = user.username
        this.fameName = user.fameName
        this.email = user.email
        this.mobile = user?.mobile ?: ''
        this.imageName = user?.imageName ?: ''
        this.accountLocked = user.accountLocked
        this.dateCreated = user?.dateCreated
        this.registrationType = user?.registrationType
        this.type = user?.type
        this.linkedSocialAccounts = user?.fetchActiveSocialAccount()
        this.imageFolder = user?.populateUserImagePath()
        this.totalFollowers = user?.totalNumberOfFollowers()
        this.followers = populateFollowDto(user?.fetchFollowers())
        this.followings = populateFollowDto(user?.fetchFollowings())
    }

    UserProfileDto(User user, Follow follow) {
        this.id = user?.id
        this.fameId = user?.fameId
        this.username = user.username
        this.fameName = user.fameName
        this.email = user.email
        this.mobile = user?.mobile ?: ''
        this.imageName = user?.imageName ?: ''
        this.accountLocked = user.accountLocked
        this.dateCreated = user?.dateCreated
        this.type = user?.type
        this.isFollower = follow ? (follow.isFollower) : false
        this.totalFollowers = user?.totalNumberOfFollowers()
    }

    static UserProfileDto createCommonResponseDto(User user) {
        return new UserProfileDto(user)
    }

    static UserProfileDto createCommonResponseDto(User user, Follow follow) {
        return new UserProfileDto(user, follow)
    }

    static List<FollowDto> populateFollowDto(List<User> userList) {
        List<FollowDto> followDtos = []
        userList?.each { User user ->
            followDtos << FollowDto.createCommonResponseDto(user)
        }
        return followDtos
    }
}
