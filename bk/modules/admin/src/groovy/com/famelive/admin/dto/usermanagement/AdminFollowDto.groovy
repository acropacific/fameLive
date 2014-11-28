package com.famelive.admin.dto.usermanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.dto.ResponseDto
import com.famelive.common.dto.usermanagement.FollowDto
import com.famelive.common.enums.usermanagement.UserRegistrationType

class AdminFollowDto extends AdminResponseDto {

    Long id
    String fameId
    String username
    String email
    String fameName
    String mobile
    String imageName
    Date dateCreated
    UserRegistrationType registrationType

    AdminFollowDto() {}

    AdminFollowDto(FollowDto followDto) {
        this.id = followDto?.id
        this.fameId = followDto?.fameId
        this.username = followDto.username
        this.fameName = followDto.fameName
        this.email = followDto.email
        this.mobile = followDto?.mobile ?: ''
        this.imageName = followDto?.imageName ?: ''
        this.dateCreated = followDto?.dateCreated
        this.registrationType = followDto?.registrationType
    }

    static AdminFollowDto createCommonResponseDto(FollowDto followDto) {
        return new AdminFollowDto(followDto)
    }
}
