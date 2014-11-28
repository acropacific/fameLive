package com.famelive.api.command.followermanagement

import com.famelive.api.command.ApiPaginationCommand
import com.famelive.common.command.followermanagement.FetchFollowersCommand
import grails.validation.Validateable

@Validateable
class ApiFetchFollowersCommand extends ApiPaginationCommand {
    Long performerId

    static constraints = {
        performerId nullable: true
    }

    @Override
    FetchFollowersCommand toRequestCommand() {
        return new FetchFollowersCommand(id: this?.id, performerId: this?.performerId)
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
