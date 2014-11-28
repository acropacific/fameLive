package com.famelive.streamManagement

import com.famelive.streamManagement.command.RequestCO
import com.famelive.streamManagement.command.ResponseCO
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ApiController {
    RequestHandlerService requestHandlerService

    def index(RequestCO requestCO) {
        response.setContentType("application/json; charset=utf8")
        ResponseCO responseCO = requestHandlerService.process(request, requestCO, params.id)
        render responseCO.toJson().toString()
    }

    def handleApiException(Exception apiException) {
//        renderResponse(new ApiResponseDto(apiException))
    }

    /*private renderResponse(ApiResponseDto responseDto) {
        render responseDto.toJSON()
    }*/
}
