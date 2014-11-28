package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.user.User

class FollowDto extends ResponseDto {

    Long id
    String fameId
    String username
    String email
    String fameName
    String mobile
    String imageName
    Date dateCreated
    UserRegistrationType registrationType

    FollowDto() {}

    FollowDto(User user) {
        this.id = user?.id
        this.fameId = user?.fameId
        this.username = user.username
        this.fameName = user.fameName
        this.email = user.email
        this.mobile = user?.mobile ?: ''
        this.imageName = user?.imageName ?: ''
        this.dateCreated = user?.dateCreated
        this.registrationType = user?.registrationType
    }

    static FollowDto createCommonResponseDto(User user) {
        return new FollowDto(user)
    }
}
