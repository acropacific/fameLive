package com.famelive

import com.famelive.api.command.chat.ApiFetchChatChannelCommand
import com.famelive.api.command.followermanagement.ApiFetchFollowersCommand
import com.famelive.api.command.followermanagement.ApiFollowPerformerCommand
import com.famelive.api.command.followermanagement.ApiUnFollowPerformerCommand
import com.famelive.api.command.notification.ApiFetchNotificationChannelsCommand
import com.famelive.api.command.search.ApiSearchEventCommand
import com.famelive.api.command.search.ApiSearchPerformerCommand
import com.famelive.api.command.slotmanagement.*
import com.famelive.api.command.usermanagement.*
import com.famelive.api.util.ApiSessionUtils
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.GrailsApplication

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Apiv1Controller extends ApiController {

    def springSecurityService
    GrailsApplication grailsApplication

    @Secured(['permitAll'])
    def configuration() {
        render apiService.configuration().toJSON()
    }

    @Secured(['permitAll'])
    def registerUser(ApiRegistrationCommand registrationCommand) {
        render apiService.registerUser(registrationCommand).toJSON()
    }

    @Secured(['permitAll'])
    def saveUserImage(ApiUserImageCommand apiUserImageCommand) {
        render apiService.saveUserImageName(apiUserImageCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def changePassword(ApiChangePasswordCommand changePasswordCommand) {
        changePasswordCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.changePassword(changePasswordCommand).toJSON()

    }

    @Secured(['permitAll'])
    def login(ApiLoginCommand loginCommand) {
        loginCommand.request = request as HttpServletRequest
        render apiService.login(loginCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def logout(ApiLogoutCommand logoutCommand) {
        logoutCommand.request = request as HttpServletRequest
        logoutCommand.response = response as HttpServletResponse
        render apiService.logout(logoutCommand).toJSON()
    }

    @Secured(['permitAll'])
    def forgotPassword(ApiForgotPasswordCommand forgotPasswordCommand) {
        render apiService.forgotPassword(forgotPasswordCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def updateUserProfile(ApiUserProfileCommand userProfileCommand) {
        userProfileCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.updateUserProfile(userProfileCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def userProfileData(ApiFetchUserProfileCommand fetchUserProfileCommand) {
        fetchUserProfileCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.fetchUserProfileData(fetchUserProfileCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def fetchSocialAccounts(ApiFetchUserSocialAccountCommand apiFetchUserSocialAccountCommand) {
        apiFetchUserSocialAccountCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.fetchSocialAccounts(apiFetchUserSocialAccountCommand).toJSON()
    }

    @Secured(['permitAll'])
    def validateForgotPasswordCode(ApiValidateForgotPasswordCodeCommand apiValidateForgotPasswordCodeCommand) {
        render apiService.validateForgotPasswordCode(apiValidateForgotPasswordCodeCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def updateForgotPassword(ApiUpdateForgotPasswordCommand apiUpdateForgotPasswordCommand) {
        render apiService.updateForgotPassword(apiUpdateForgotPasswordCommand).toJSON()
    }

    @Secured(['permitAll'])
    def checkFameNameAvailability(ApiCheckFameNameAvailabilityCommand apiCheckFameNameAvailabilityCommand) {
        render apiService.isFameNameAvailable(apiCheckFameNameAvailabilityCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchGenreList(ApiFetchGenreCommand apiFetchGenreCommand) {
        render apiService.fetchAllPublishedGenre(apiFetchGenreCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def createEvent(ApiEventCommand eventCommand) {
        eventCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.createEvent(eventCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def editEvent(ApiEditEventCommand eventCommand) {
        eventCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.editEvent(eventCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def saveEventImage(ApiEventImageCommand apiEventImageCommand) {
        apiEventImageCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.saveEventImage(apiEventImageCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def cancelEvent(ApiCancelEventCommand apiCancelEventCommand) {
        apiCancelEventCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.cancelEvent(apiCancelEventCommand).toJSON()
    }

    @Secured(['permitAll'])
    def userEventList(ApiFetchUserEventListCommand apiFetchEventListCommand) {
        render apiService.fetchAllEventsOfUser(apiFetchEventListCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchEventList(ApiFetchEventListCommand apiFetchEventListCommand) {
        render apiService.fetchEventList(apiFetchEventListCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchEventDetails(ApiFetchEventDetailsCommand apiFetchEventDetailsCommand) {
        render apiService.fetchEventDetails(apiFetchEventDetailsCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def addSocialAccount(ApiUserSocialAccountCommand addUserSocialAccountCommand) {
        addUserSocialAccountCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.addSocialAccount(addUserSocialAccountCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def removeSocialAccount(ApiUserSocialAccountCommand addUserSocialAccountCommand) {
        addUserSocialAccountCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.removeSocialAccount(addUserSocialAccountCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def follow(ApiFollowPerformerCommand apiFollowPerformerCommand) {
        apiFollowPerformerCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.follow(apiFollowPerformerCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def unFollow(ApiUnFollowPerformerCommand apiUnFollowPerformerCommand) {
        apiUnFollowPerformerCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.unFollow(apiUnFollowPerformerCommand).toJSON()
    }

    @Secured(['permitAll'])
    def searchPerformer(ApiSearchPerformerCommand apiSearchPerformerCommand) {
        render apiService.searchPerformer(apiSearchPerformerCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchPerformerDetail(ApiFetchPerformerProfileCommand apiFetchPerformerProfileCommand) {
        apiFetchPerformerProfileCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.fetchPerformerDetail(apiFetchPerformerProfileCommand).toJSON()
    }

    @Secured(['permitAll'])
    def searchEvent(ApiSearchEventCommand apiSearchEventCommand) {
        render apiService.searchEvent(apiSearchEventCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def followers(ApiFetchFollowersCommand apiFollowersCommand) {
        apiFollowersCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.fetchFollowers(apiFollowersCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchSocialTemplate(ApiFetchSocialTemplateCommand apiFetchSocialTemplateCommand) {
        render apiService.fetchSocialTemplate(apiFetchSocialTemplateCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchNotificationChannels(ApiFetchNotificationChannelsCommand apiFetchNotificationChannelsCommand) {
        apiFetchNotificationChannelsCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.fetchNotificationChannels(apiFetchNotificationChannelsCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def changeEmail(ApiChangeEmailCommand apiChangeEmailCommand) {
        apiChangeEmailCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.changeEmail(apiChangeEmailCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def isAccountVerified(ApiCheckUserAccountCommand apiCheckUserAccountCommand) {
        apiCheckUserAccountCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.checkUserAccount(apiCheckUserAccountCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def verifyEmail(ApiVerifyUserEmailCommand apiVerifyUserEmailCommand) {
        apiVerifyUserEmailCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.verifyEmail(apiVerifyUserEmailCommand).toJSON()
    }

    @Secured(['ROLE_USER'])
    def sendEmailVerificationCode(ApiSendEmailVerificationCodeCommand apiSendEmailVerificationCodeCommand) {
        apiSendEmailVerificationCodeCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.sendEmailVerificationCode(apiSendEmailVerificationCodeCommand).toJSON()
    }

    @Secured(['permitAll'])
    def fetchChatChannel(ApiFetchChatChannelCommand apiFetchChatChannelCommand) {
        apiFetchChatChannelCommand.id = ApiSessionUtils.fetchCurrentUserId()
        render apiService.fetchChatChannel(apiFetchChatChannelCommand).toJSON()
    }
}
