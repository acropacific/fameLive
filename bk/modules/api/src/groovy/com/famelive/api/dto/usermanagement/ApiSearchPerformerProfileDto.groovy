package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.dto.ApiResponseDto
import com.famelive.common.dto.usermanagement.UserProfileDto

@APIResponseClass
class ApiSearchPerformerProfileDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String username
    @APIResponseField(include = true)
    public String email
    @APIResponseField(include = true)
    public String fameName
    @APIResponseField(include = true)
    public String fameId
    @APIResponseField(include = true)
    public String mobile
    @APIResponseField(include = true)
    public String imageName

    ApiSearchPerformerProfileDto() {}

    ApiSearchPerformerProfileDto(UserProfileDto userProfileDto) {
        this.username = userProfileDto.username
        this.fameName = userProfileDto.fameName ?: ''
        this.email = userProfileDto.email
        this.fameId=userProfileDto?.fameId
        this.mobile = userProfileDto?.mobile ?: ''
        this.imageName = userProfileDto?.imageName ?: ''
    }

    static ApiSearchPerformerProfileDto createApiResponseDto(UserProfileDto userProfileDto) {
        return new ApiSearchPerformerProfileDto(userProfileDto)
    }
}
