package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.constant.Constraints
import com.famelive.common.exceptions.*
import grails.validation.Validateable

@Validateable
class ChangePasswordCommand extends AuthenticationTokenCommand {

    String email
    String password
    String newPassword
    String confirmPassword

    static constraints = {
        email nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEmailException()
            }
        }
        password nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankPasswordException()
            } else if (val.length() < Constraints.PASSWORD_MIN_SIZE) {
                throw new PasswordMinLengthException()
            }
        }
        newPassword nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankNewPasswordException()
            } else if (val.length() < Constraints.PASSWORD_MIN_SIZE) {
                throw new PasswordMinLengthException()
            } else if (!val.matches(Constraints.PASSWORD_PATTERN)) {
                throw new PasswordMinLengthException()
            }
        }
        confirmPassword nullable: true, validator: { val, obj ->
            if (val != obj.newPassword) {
                throw new NewPasswordConfirmPasswordNotSameException()
            }
        }
    }
}
