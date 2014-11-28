package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminPaginationCommand
import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.util.AdminDateTimeUtils
import com.famelive.common.command.slotmanagement.FetchEventsCommand
import grails.validation.Validateable

@Validateable
class AdminFetchAllEventCommand extends AdminPaginationCommand {

    String memberName
    String email
    String startDate

    static constraints = {
        memberName nullable: true
        email nullable: true
        startDate nullable: true
        max nullable: true
        sort nullable: true
        offset nullable: true
        order nullable: true
    }

    AdminFetchAllEventCommand() {}

    AdminFetchAllEventCommand(AdminFetchAllEventCommand paginationCommand) {
        this.max = this.getMaxValue(paginationCommand?.max)
        this.sort = this.getSortValue(paginationCommand?.sort)
        this.order = this.getOrderValue(paginationCommand?.order)
        this.offset = this.getOffsetValue(paginationCommand?.offset)
        this.startDate = paginationCommand?.startDate
        this.memberName = paginationCommand?.memberName
        this.email = paginationCommand?.email
    }

    @Override
    FetchEventsCommand toRequestCommand() {
        new FetchEventsCommand(id: this?.id, max: this?.max, sort: this?.sort, order: this?.order, offset: this?.offset, memberName: this.memberName, email: this?.email, startDateTime: this?.startDateTime, endDateTime: this?.endDateTime)
    }

    @Override
    Integer getMaxValue(Integer max) {
        return Math.min(max ?: AdminConstants.EVENT_RESULT_PER_PAGE, AdminConstants.DEFAULT_MAX_PAGINATION)
    }

    @Override
    String getOrderValue(String order) {
        return order ?: AdminConstants.EVENT_DEFAULT_ORDER
    }

    @Override
    String getSortValue(String sort) {
        return sort ?: AdminConstants.EVENT_DEFAULT_SORT_COLUMN
    }

    @Override
    Integer getOffsetValue(Integer offset) {
        return offset ?: AdminConstants.DEFAULT_OFFSET
    }

    Date getStartDateTime() {
        (this?.startDate) ? AdminDateTimeUtils?.formatDate(AdminConstants.DATE_FORMAT, this?.startDate) : null
    }

    Date getEndDateTime() {
        (this?.startDate) ? AdminDateTimeUtils?.getDateWithEndTime(this?.getStartDateTime()) : null
    }
}
