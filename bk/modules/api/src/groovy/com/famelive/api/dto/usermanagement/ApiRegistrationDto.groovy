package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.RegistrationDto

@APIResponseClass
class ApiRegistrationDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String username
    @APIResponseField(include = true)
    public String email
    @APIResponseField(include = true)
    public String fameName
    @APIResponseField(include = true)
    public String mobile
    @APIResponseField(include = true)
    public Date dateCreated
    @APIResponseField(include = true)
    public String imageFolder

    ApiRegistrationDto() {}

    ApiRegistrationDto(RegistrationDto commonRegistrationDto) {
        this.username = commonRegistrationDto.username
        this.fameName = commonRegistrationDto.fameName ?: ''
        this.email = commonRegistrationDto.email
        this.mobile = commonRegistrationDto?.mobile ?: ''
        this.dateCreated = commonRegistrationDto?.dateCreated
        this.imageFolder=commonRegistrationDto?.imageFolder
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.REGISTER_USER.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiRegistrationDto createApiResponseDto(RegistrationDto registrationDto) {
        return new ApiRegistrationDto(registrationDto)
    }

}
