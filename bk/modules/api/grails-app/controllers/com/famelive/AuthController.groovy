package com.famelive

import com.odobo.grails.plugin.springsecurity.rest.RestOauthService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder

@Secured(['permitAll'])
class AuthController {
    SpringSecurityService springSecurityService
    RestOauthService restOauthService

    def authenticate() {
//        springSecurityService.authentication
        //http://alvarosanchez.github.io/grails-spring-security-rest/docs/guide/oauth.html#google.
        //org.springframework.security.core.context.SecurityContextHolder.context.authentication.principal
        //restOauthService.
        SecurityContextHolder.context
        println 'AuthController.authenticate'
        render 'authenticate params.token:: ' + params.token + "  " + springSecurityService.authentication.authorities
    }

    def fetchData() {
        render params
    }
}
