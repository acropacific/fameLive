package com.famelive.api.command.slotmanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.api.command.ApiPaginationCommand
import com.famelive.common.command.slotmanagement.FetchEventsCommand
import grails.validation.Validateable

@Validateable
class ApiFetchUserEventListCommand extends ApiPaginationCommand {

    Long performerId

    static constraints = {
        performerId nullable: true
        max nullable: true
        order nullable: true
        sort nullable: true
        offset nullable: true
    }

    @Override
    FetchEventsCommand toRequestCommand() {
        return new FetchEventsCommand(id: this?.performerId, max: this?.max, order: this?.order, sort: this?.sort, offset: this?.offset)
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
