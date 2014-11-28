package com.famelive

import com.famelive.api.command.ApiHandleRequestCommand
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.exceptions.ApiException
import com.famelive.api.exceptions.ApiUserAccountBlockException
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ApiController {
    def apiService
    def messageSource

    def index(ApiHandleRequestCommand requestCommand) {
        forward(controller: "Api${params.id}", action: "${requestCommand?.actionName}")
    }

    def handleApiUserAccountBlockException(ApiUserAccountBlockException apiUserAccountBlockException) {
        renderResponse(new ApiResponseDto(apiUserAccountBlockException))
    }

    def handleApiException(ApiException apiException) {
        renderResponse(new ApiResponseDto(apiException))
    }

    private renderResponse(ApiResponseDto responseDto) {
        render responseDto.toJSON()
    }
}
