package com.odobo.grails.plugin.springsecurity.rest

import org.springframework.security.core.AuthenticationException

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by anil on 18/9/14.
 *
 *
 */
class CustomRestAuthenticationFailureHandler extends RestAuthenticationFailureHandler {
    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug "Setting status code to ${statusCode}"
        //response.setStatus(statusCode)
        response << '{"status":"0","message":"Invalid Email id / Password","detail":"","data":{}}'

    }
}
