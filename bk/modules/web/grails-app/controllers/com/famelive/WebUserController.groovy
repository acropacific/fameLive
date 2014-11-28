package com.famelive

import com.famelive.web.command.WebChangePasswordCommand
import com.famelive.web.command.WebFetchUserProfileCommand
import com.famelive.web.command.WebRegistrationCommand
import com.famelive.web.command.WebUserProfileCommand
import com.famelive.web.dto.WebUserProfileDTO
import com.famelive.web.exceptions.WebValidationException
import com.famelive.web.utils.WebSessionUtils
import grails.plugin.springsecurity.annotation.Secured

class WebUserController {

    def webUserService

    @Secured(['permitAll'])
    def index() {

    }

    @Secured(['permitAll'])
    def register() {
    }

    @Secured(['permitAll'])
    def saveUserDetail(WebRegistrationCommand registrationCommand) {
        try {
            webUserService.saveUserDetail(registrationCommand)
        } catch (WebValidationException webValidationException) {
            webValidationException.printStackTrace(System.out)
        }
        redirect controller: 'login', action: 'auth'
    }

    @Secured(['ROLE_USER'])
    def userProfile(WebFetchUserProfileCommand fetchUserProfileCommand) {
        fetchUserProfileCommand.id = WebSessionUtils.fetchCurrentUserId()
        WebUserProfileDTO userDetail = webUserService.fetchUserProfile(fetchUserProfileCommand)
        [userDetail: userDetail]
    }

    @Secured(['ROLE_USER'])
    def updateUserProfile(WebFetchUserProfileCommand fetchUserProfileCommand) {
        fetchUserProfileCommand.id = WebSessionUtils.fetchCurrentUserId()
        WebUserProfileDTO userDetail = webUserService.fetchUserProfile(fetchUserProfileCommand)
        [userDetail: userDetail]
    }

    @Secured(['ROLE_USER'])
    def updateUserDetail(WebUserProfileCommand userProfileCommand) {
        try {
            webUserService.updateUserDetails(userProfileCommand)
        } catch (WebValidationException webValidationException) {
            webValidationException.printStackTrace(System.out)
        }
        redirect controller: 'webUser', action: 'userProfile', params: [id: userProfileCommand?.id]
    }

    @Secured(['ROLE_USER'])
    def changePassword(WebFetchUserProfileCommand fetchUserProfileCommand) {
        fetchUserProfileCommand.id = WebSessionUtils.fetchCurrentUserId()
        WebUserProfileDTO userDetail = webUserService.fetchUserProfile(fetchUserProfileCommand)
        [userDetail: userDetail]
    }

    @Secured(['ROLE_USER'])
    def updatePassword(WebChangePasswordCommand changePasswordCommand) {
        try {
            changePasswordCommand.id = WebSessionUtils.fetchCurrentUserId()
            webUserService.updatePassword(changePasswordCommand)
        } catch (WebValidationException webValidationException) {
            webValidationException.printStackTrace(System.out)
        }
        redirect controller: 'webUser', action: 'userProfile', params: [id: changePasswordCommand?.id]
    }

}
