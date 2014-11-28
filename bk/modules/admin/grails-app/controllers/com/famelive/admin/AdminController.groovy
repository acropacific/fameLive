package com.famelive.admin

import com.famelive.admin.command.AdminUserSearchCommand
import com.famelive.admin.command.usermanagement.AdminUserProfileCommand
import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.dto.AdminUsersDto
import com.famelive.admin.dto.template.AdminNotificationTemplateDto
import com.famelive.admin.dto.template.AdminSocialTemplateDto
import com.famelive.admin.dto.usermanagement.AdminUserInformationDto
import com.famelive.admin.exception.AdminException
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class AdminController {

    def adminService

    @Secured(['permitAll'])
    def index() {
        return redirect(controller: 'admin', action: 'userList')
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def userList(AdminUserSearchCommand adminUserSearchCommand) {
        params.max = Math.min(adminUserSearchCommand?.max ?: AdminConstants.USER_RESULT_PER_PAGE, AdminConstants.DEFAULT_MAX_PAGINATION)
        AdminUserSearchCommand adminUserSearchDto = new AdminUserSearchCommand(adminUserSearchCommand)
        AdminUsersDto adminUsersDto = adminService.fetchUserList(adminUserSearchDto)
        [adminUsersDto: adminUsersDto, adminUserSearchCommand: adminUserSearchCommand]
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def userProfile(AdminUserProfileCommand adminUserProfileCommand) {
        try {
            AdminUserInformationDto adminUserInformationDto = adminService.fetchUserInformation(adminUserProfileCommand)
            [adminUserInformationVO: adminUserInformationDto]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'admin', action: 'userList'
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def blockOrUnBlockUserAccount(AdminUserProfileCommand adminUserProfileCommand) {
        try {
            adminService.blockOrUnBlockUserAccount(adminUserProfileCommand)
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
        redirect controller: 'admin', action: 'userProfile', params: [userId: adminUserProfileCommand?.userId]
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def socialTemplates() {
        try {
            AdminSocialTemplateDto adminSocialTemplateDto = adminService.fetchSocialSharingTemplates()
            [adminSocialTemplateVo:adminSocialTemplateDto]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }
    @Secured(['ROLE_SUPER_ADMIN'])
    def notificationTemplates() {
        try {
            AdminNotificationTemplateDto notificationTemplate = adminService.fetchPushNotificationTemplate()
            [notificationTemplateVo:notificationTemplate]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }
}
