package com.famelive.api.command.search

import com.famelive.api.command.ApiPaginationCommand
import com.famelive.common.command.usernamagement.UserSearchCommand
import grails.validation.Validateable

@Validateable
class ApiSearchPerformerCommand extends ApiPaginationCommand {

    String fameName

    static constraints = {
        fameName nullable: true
        max nullable: true
        order nullable: true
        sort nullable: true
        offset nullable: true
    }

    @Override
    UserSearchCommand toRequestCommand() {
        return new UserSearchCommand(id: this?.id, fameName: this?.fameName, max: this?.max, offset: this?.offset, order: this?.order, sort: this?.sort)
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
