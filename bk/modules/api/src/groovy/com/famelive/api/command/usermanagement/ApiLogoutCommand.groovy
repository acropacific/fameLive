package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.common.command.RequestCommand
import grails.validation.Validateable

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by anil on 26/9/14.
 */

@Validateable
class ApiLogoutCommand extends ApiRequestCommand {
    HttpServletRequest request
    HttpServletResponse response
    String email

    @Override
    RequestCommand toRequestCommand() {
        return null
    }
}
