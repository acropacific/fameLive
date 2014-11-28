package com.famelive.api

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class TestSecureRestServiceController {

    SpringSecurityService springSecurityService

    @Secured(['permitAll'])
    def index() {
        render 'ok'
    }

    //@Secured(['ROLE_SUPER_ADMIN', 'ROLE_ADMIN'])
    @Secured(['ROLE_SUPER_ADMIN'])
    def getName() {
        println springSecurityService?.currentUser?.username
        println springSecurityService.authentication.authorities
        render springSecurityService?.currentUser?.username
    }

}
