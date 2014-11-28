package com.famelive.common.command

import grails.validation.Validateable

@Validateable
abstract class PaginationCommand extends AuthenticationTokenCommand {
    Integer max
    String sort
    String order
    Integer offset
}
