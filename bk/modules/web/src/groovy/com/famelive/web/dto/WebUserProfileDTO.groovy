package com.famelive.web.dto

import com.famelive.common.dto.usermanagement.UserProfileDto

class WebUserProfileDTO extends WebResponseDTO {

    Long id
    String username
    String email
    String fameName
    String mobile
    String imageName
    Date dateCreated

    WebUserProfileDTO() {}

    WebUserProfileDTO(UserProfileDto user) {
        this.id = user.id
        this.username = user.username
        this.fameName = user.fameName
        this.email = user.email
        this.mobile = user?.mobile ?: ''
        this.imageName = user?.imageName ?: ''
        this.dateCreated = user?.dateCreated
    }

    static WebUserProfileDTO createWebResponseDto(UserProfileDto user) {
        return new WebUserProfileDTO(user)
    }
}
