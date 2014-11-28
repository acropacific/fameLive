package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiRequestCommand
import com.famelive.api.enums.ApiUserRegistrationType
import com.famelive.api.exceptions.ApiInvalidLoginCredentialException
import com.famelive.common.command.RequestCommand
import grails.validation.Validateable

import javax.servlet.http.HttpServletRequest

@Validateable
class ApiLoginCommand extends ApiRequestCommand {

    String email
    String password
    ApiUserRegistrationType medium
    String oAuthToken
    HttpServletRequest request = null

    static constraints = {
        password nullable: true
        oAuthToken nullable: true, validator: { val, obj ->
            if (obj.medium != ApiUserRegistrationType.MANUAL) {
                if (!val) {
                    throw new ApiInvalidLoginCredentialException()
                }
            }
        }
    }

    @Override
    RequestCommand toRequestCommand() {
        return null
    }
}
