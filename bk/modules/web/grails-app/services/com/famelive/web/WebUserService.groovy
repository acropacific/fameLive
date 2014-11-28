package com.famelive.web

import com.famelive.common.exceptions.ValidationException
import com.famelive.web.command.WebChangePasswordCommand
import com.famelive.web.command.WebFetchUserProfileCommand
import com.famelive.web.command.WebRegistrationCommand
import com.famelive.web.command.WebUserProfileCommand
import com.famelive.web.dto.WebUserProfileDTO
import com.famelive.web.exceptions.WebException
import com.famelive.web.exceptions.WebValidationException

class WebUserService {

    def userService

    void saveUserDetail(WebRegistrationCommand registrationCommand) throws WebException {
        try {
            userService.registerUser(registrationCommand.toRequestCommand())
        } catch (ValidationException validationException) {
            throw new WebValidationException(validationException)
        }
    }

    WebUserProfileDTO fetchUserProfile(WebFetchUserProfileCommand fetchUserProfileCommand) throws WebException {
        try {
            WebUserProfileDTO.createWebResponseDto(userService.fetchUserProfileData(fetchUserProfileCommand.toRequestCommand()))
        } catch (ValidationException validationException) {
            throw new WebValidationException(validationException)
        }
    }

    void updateUserDetails(WebUserProfileCommand userProfileCommand) {
        try {
            userService.updateUserProfile(userProfileCommand.toRequestCommand())
        } catch (ValidationException validationException) {
            throw new WebValidationException(validationException)
        }
    }

    void updatePassword(WebChangePasswordCommand webChangePasswordCommand) {
        try {
            userService.changePassword(webChangePasswordCommand.toRequestCommand())
        } catch (ValidationException validationException) {
            throw new WebValidationException(validationException)
        }
    }
}
