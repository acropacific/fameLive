package com.famelive.api

import com.famelive.common.user.User
import com.odobo.grails.plugin.springsecurity.rest.RestAuthenticationToken
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//import grails.transaction.Transactional
class APISecurityService {

    def userDetailsService
    def tokenGenerator
    def tokenStorageService
    AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource
    AuthenticationManager authenticationManager
    def springSecurityService

    String loginAsUser(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.email, true)
        String tokenValue = tokenGenerator.generateToken()
        tokenStorageService.storeToken(tokenValue, userDetails)
        Authentication authenticationResult = new RestAuthenticationToken(userDetails, null, userDetails.authorities, tokenValue)
        SecurityContextHolder.context.setAuthentication(authenticationResult)
        return tokenValue
    }

    boolean authenticateUser(HttpServletRequest request, String username, String password) {
        boolean isAuthenticated = false
        try {
            UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username, password)
            if (!authenticationRequest.principal || !authenticationRequest.credentials) {
                log.debug "Username and/or password parameters are missing. Setting status to ${HttpServletResponse.SC_BAD_REQUEST}"
            } else {
                authenticationRequest.details = authenticationDetailsSource.buildDetails(request)
                Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest)
                if (authenticationResult.authenticated) {
                    isAuthenticated = true
                }
            }
        } catch (Exception e) {
            println "Exception occurred while authenticating user through rest call"
            e.printStackTrace()
        }
        return isAuthenticated
    }

    boolean logoutUser(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        boolean isLoggedOut = false
        String tokenValue = servletRequest.getHeader("x-authtoken") ?: servletRequest.getHeader("X-Auth-Token")
        if (tokenValue) {
            log.debug "Token found: ${tokenValue}"
            try {
                log.debug "Trying to remove the token"
                tokenStorageService.removeToken tokenValue
                isLoggedOut = true
            } catch (TokenNotFoundException tnfe) {
                tnfe.printStackTrace(System.out)
            }
        } else {
            log.debug "Token is missing. Sending a 400 Bad Request response"
        }
        return isLoggedOut
    }
}
