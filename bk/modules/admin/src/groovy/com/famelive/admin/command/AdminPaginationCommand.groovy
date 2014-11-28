package com.famelive.admin.command

import grails.validation.Validateable

@Validateable
abstract class AdminPaginationCommand extends AdminAuthenticationCommand {
    Integer max
    String sort
    String order
    Integer offset

    abstract Integer getMaxValue(Integer max)

    abstract String getOrderValue(String order)

    abstract String getSortValue(String sort)

    abstract Integer getOffsetValue(Integer offset)
}
