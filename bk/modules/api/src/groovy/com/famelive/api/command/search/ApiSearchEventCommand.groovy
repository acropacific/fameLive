package com.famelive.api.command.search

import com.famelive.api.command.ApiPaginationCommand
import com.famelive.common.command.slotmanagement.FetchEventsCommand
import grails.validation.Validateable

@Validateable
class ApiSearchEventCommand extends ApiPaginationCommand {

    Long genreId

    static constraints = {
        genreId nullable: true
        max nullable: true
        order nullable: true
        sort nullable: true
        offset nullable: true
    }

    @Override
    FetchEventsCommand toRequestCommand() {
        return new FetchEventsCommand(id: this?.id, genreId: this?.genreId, max: this?.max, offset: this?.offset, order: this?.order, sort: this?.sort)
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
