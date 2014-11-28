package com.famelive.api.command.slotmanagement

import com.famelive.api.command.ApiPaginationCommand
import com.famelive.api.enums.ApiEventStatus
import com.famelive.common.command.slotmanagement.EventListCommand
import grails.validation.Validateable

@Validateable
class ApiFetchEventListCommand extends ApiPaginationCommand {

    ApiEventStatus eventStatus

    static constraints = {
        eventStatus nullable: true
    }

    @Override
    EventListCommand toRequestCommand() {
        return new EventListCommand(eventStatus: this?.eventStatus?.eventStatus, max: this?.max, order: this?.order, sort: this?.sort, offset: this?.offset)
    }

    @Override
    Integer getMaxValue(Integer max) {
        return null
    }

    @Override
    String getOrderValue(String order) {
        return null
    }

    @Override
    String getSortValue(String sort) {
        return null
    }

    @Override
    Integer getOffsetValue(Integer offset) {
        return null
    }
}
