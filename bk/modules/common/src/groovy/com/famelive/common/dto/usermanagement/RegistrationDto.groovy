package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class RegistrationDto extends ResponseDto {

    String username
    String email
    String fameName
    String mobile
    String imageName
    Date dateCreated
    String imageFolder

    RegistrationDto() {}

    RegistrationDto(User user) {
        this.username = user.username
        this.fameName = user.fameName
        this.email = user.email
        this.mobile = user?.mobile ?: ''
        this.imageName = user?.imageName ?: ''
        this.dateCreated = user?.dateCreated
        this.imageFolder = user?.populateUserImagePath()
    }

    static RegistrationDto createCommonResponseDto(User user) {
        return new RegistrationDto(user)
    }
}
