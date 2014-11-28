package com.famelive.api

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class AuthorizationController {
    OAuthLoginService oAuthLoginService

    def login() {
//        BaseOAuthClient client=restOauthService.getClient("facebook")
        println 'inside AuthorizationController login'
        //oAuthLoginService

//        redirect(controller: 'restOauth',action: 'authenticate',params: [provider:'facebook',callback:'/api/auth/login'])
//        return
        render 'logined'
    }

    def logout() {
        println 'inside AuthorizationController logout'
        render 'loggedout'
    }
}
