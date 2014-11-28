package com.famelive.common.command.usernamagement

import com.famelive.common.command.RequestCommand
import com.famelive.common.constant.Constraints
import com.famelive.common.exceptions.*
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class UpdateForgotPasswordCommand extends RequestCommand {

    String email
    String password
    String confirmPassword
    String sessionEmail

    static constraints = {
        email nullable: true, validator: { val, object ->
            if (!val) {
                throw new BlankEmailException()
            } else if (!User.findByEmail(val)) {
                throw new EmailNotFoundException()
            }
        }
        password nullable: true, validator: { val, object ->
            if (!val) {
                throw new BlankPasswordException()
            } else if (val.length() < Constraints.PASSWORD_MIN_SIZE) {
                throw new PasswordMinLengthException()
            }
        }
        confirmPassword nullable: true, validator: { val, object ->
            if (val != object.password) {
                throw new PasswordNotEqualException()
            }
        }
        sessionEmail nullable: true, validator: { val, object ->
            if (!val || val != object.email) {
                throw new UnauthorizedEmailException()
            }
        }
    }
}
