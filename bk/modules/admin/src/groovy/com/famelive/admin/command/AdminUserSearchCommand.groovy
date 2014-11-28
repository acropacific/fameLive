package com.famelive.admin.command

import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.enums.AdminUserRegistrationType
import com.famelive.admin.enums.AdminUserType
import com.famelive.common.command.usernamagement.UserSearchCommand
import grails.validation.Validateable

@Validateable
class AdminUserSearchCommand extends AdminPaginationCommand {

    String fameName
    String email
    String fameId
    AdminUserRegistrationType registrationType
    AdminUserType type

    static constraints = {
        fameName nullable: true
        email nullable: true
        fameId nullable: true
        registrationType nullable: true
        type nullable: true
        max nullable: true
        sort nullable: true
        offset nullable: true
        order nullable: true
    }

    AdminUserSearchCommand() {}

    AdminUserSearchCommand(AdminUserSearchCommand paginationCommand) {
        this.max = this.getMaxValue(paginationCommand?.max)
        this.sort = this.getSortValue(paginationCommand?.sort)
        this.order = this.getOrderValue(paginationCommand?.order)
        this.offset = this.getOffsetValue(paginationCommand?.offset)
        this.fameName = paginationCommand?.fameName
        this.email = paginationCommand?.email
        this.fameId = paginationCommand?.fameId
        this.registrationType = paginationCommand?.registrationType
        this.type = paginationCommand?.type
    }

    Integer getMaxValue(Integer max) {
        Math.min(max ?: AdminConstants.USER_RESULT_PER_PAGE, AdminConstants.DEFAULT_MAX_PAGINATION)
    }

    String getOrderValue(String order) {
        order ?: AdminConstants.USER_DEFAULT_ORDER
    }

    String getSortValue(String sort) {
        sort ?: AdminConstants.USER_DEFAULT_SORT_COLUMN
    }

    Integer getOffsetValue(Integer offset) {
        offset ?: AdminConstants.DEFAULT_OFFSET
    }

    @Override
    UserSearchCommand toRequestCommand() {
        new UserSearchCommand(max: this?.max, sort: this?.sort, order: this?.order, offset: this?.offset, email: this?.email, fameName: this?.fameName, fameId: this?.fameId, registrationTypes: this?.registrationType?.registrationTypes, types: this?.type?.userTypes)
    }
}
