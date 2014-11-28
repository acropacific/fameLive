package com.famelive.admin

import com.famelive.admin.command.AdminUserSearchCommand
import com.famelive.admin.command.template.AdminFetchPushNotificationTemplateCommand
import com.famelive.admin.command.template.AdminFetchSocialTemplateCommand
import com.famelive.admin.command.usermanagement.AdminFetchUserProfileCommand
import com.famelive.admin.command.usermanagement.AdminUserProfileCommand
import com.famelive.admin.dto.AdminUserProfileDto
import com.famelive.admin.dto.AdminUsersDto
import com.famelive.admin.dto.template.AdminNotificationTemplateDto
import com.famelive.admin.dto.template.AdminSocialTemplateDto
import com.famelive.admin.dto.usermanagement.AdminUserInformationDto
import com.famelive.admin.enums.AdminPushNotification
import com.famelive.admin.enums.AdminSocialAccount
import com.famelive.admin.exception.AdminException
import com.famelive.common.command.slotmanagement.FetchEventsCommand
import com.famelive.common.command.template.FetchPushNotificationTemplateCommand
import com.famelive.common.command.template.FetchSocialTemplateCommand
import com.famelive.common.command.usernamagement.FetchUserProfileCommand
import com.famelive.common.command.usernamagement.UserSearchCommand
import com.famelive.common.dto.slotmanagement.FetchEventsDto
import com.famelive.common.dto.socialtemplate.NotificationTemplateDto
import com.famelive.common.dto.socialtemplate.SocialTemplateDto
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.dto.usermanagement.UsersDto
import com.famelive.common.exceptions.CommonException

class AdminService {

    def userService
    def eventService
    def templateService

    AdminUsersDto fetchUserList(AdminUserSearchCommand adminUserSearchCommand) {
        UserSearchCommand userSearchCommand = adminUserSearchCommand.toRequestCommand()
        UsersDto usersDto = userService.fetchUserList(userSearchCommand)
        AdminUsersDto adminUsersDto = AdminUsersDto.createAdminResponseDto(usersDto)
        return adminUsersDto
    }

    AdminUserProfileDto fetchUserProfileData(AdminFetchUserProfileCommand adminFetchUserProfileCommand) throws AdminException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = adminFetchUserProfileCommand.toRequestCommand()
            UserProfileDto userProfileDto = userService.fetchUserProfileData(fetchUserProfileCommand)
            AdminUserProfileDto adminUserProfileDto = AdminUserProfileDto.createAdminResponseDto(userProfileDto)
            return adminUserProfileDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminUserInformationDto fetchUserInformation(AdminUserProfileCommand adminUserProfileCommand) throws AdminException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = adminUserProfileCommand.toRequestCommand()
            FetchEventsCommand fetchEventsCommand = adminUserProfileCommand.toFetchEventsCommand()
            UserProfileDto userProfileDto = userService.fetchUserProfileData(fetchUserProfileCommand)
            FetchEventsDto fetchEventsDto = eventService.fetchAllEventsOfUser(fetchEventsCommand)
            AdminUserInformationDto adminUserInformationDto = AdminUserInformationDto.createAdminResponseDto(userProfileDto, fetchEventsDto)
            return adminUserInformationDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    void blockOrUnBlockUserAccount(AdminUserProfileCommand adminUserProfileCommand) throws AdminException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = adminUserProfileCommand.toRequestCommand()
            userService.blockOrUnBlockUserAccount(fetchUserProfileCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminSocialTemplateDto fetchSocialSharingTemplates() throws AdminException {
        try {
            AdminFetchSocialTemplateCommand adminFetchSocialTemplateCommand = new AdminFetchSocialTemplateCommand(adminSocialAccount: AdminSocialAccount.ALL)
            FetchSocialTemplateCommand fetchSocialTemplateCommand = adminFetchSocialTemplateCommand.toRequestCommand()
            SocialTemplateDto socialTemplateDto = templateService.fetchSocialTemplate(fetchSocialTemplateCommand)
            AdminSocialTemplateDto adminSocialTemplateDto = AdminSocialTemplateDto.createCommonResponseDto(socialTemplateDto)
            return adminSocialTemplateDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminNotificationTemplateDto fetchPushNotificationTemplate() throws AdminException {
        try {
            AdminFetchPushNotificationTemplateCommand adminFetchPushNotificationTemplateCommand = new AdminFetchPushNotificationTemplateCommand(adminPushNotification: AdminPushNotification.ALL)
            FetchPushNotificationTemplateCommand fetchPushNotificationTemplateCommand = adminFetchPushNotificationTemplateCommand.toRequestCommand()
            NotificationTemplateDto notificationTemplateDto = templateService.fetchPushNotificationTemplate(fetchPushNotificationTemplateCommand)
            AdminNotificationTemplateDto adminNotificationTemplateDto = AdminNotificationTemplateDto.createCommonResponseDto(notificationTemplateDto)
            return adminNotificationTemplateDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }
}
