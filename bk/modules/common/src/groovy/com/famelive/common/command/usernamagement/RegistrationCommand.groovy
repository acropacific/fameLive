package com.famelive.common.command.usernamagement

import com.famelive.common.command.RequestCommand
import com.famelive.common.constant.Constraints
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.enums.usermanagement.UserType
import com.famelive.common.exceptions.*
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class RegistrationCommand extends RequestCommand {
    String email
    String password
    String username
    String fameName
    String mobile
    String imageName
    UserRegistrationType medium
    UserType userType

    static constraints = {
        username nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankUserNameException()
            } else if (val.length() > Constraints.USERNAME_MAX_SIZE) {
                throw new UserNameMaxLengthException()
            }
        }
        password nullable: true, validator: { val, obj ->
            if (obj.medium == UserRegistrationType.MANUAL) {
                if (!val) {
                    throw new BlankPasswordException()
                } else if (val.length() < Constraints.PASSWORD_MIN_SIZE) {
                    throw new PasswordMinLengthException()
                } else if (!val.matches(Constraints.PASSWORD_PATTERN)) {
                    throw new PasswordPatternException()
                }
            }
        }
        email nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEmailException()
            } else if (User.countByEmail(val)) {
                throw new UniqueEmailException()
            }
        }
        mobile nullable: true
        fameName nullable: true, validator: { val, obj ->
            if (obj.userType == UserType.PERFORMER) {
                if (!val) {
                    throw new BlankFameNameException()
                } else if (val.length() > Constraints.FAMENAME_MAX_SIZE) {
                    throw new FameNameMaxLengthException()
                } else if (User.countByFameName(val)) {
                    throw new UniqueFameNameException()
                }
            }
        }
        medium nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankUserRegistrationTypeException()
            }
        }
        userType nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankUserTypeException()
            }
        }
    }
}
