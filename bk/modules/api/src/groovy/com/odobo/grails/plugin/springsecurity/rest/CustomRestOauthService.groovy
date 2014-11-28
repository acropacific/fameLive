package com.odobo.grails.plugin.springsecurity.rest

import com.famelive.api.OAuthLoginService
import com.odobo.grails.plugin.springsecurity.rest.oauth.OauthUser
import org.pac4j.core.context.WebContext
import org.pac4j.core.credentials.Credentials
import org.pac4j.core.profile.CommonProfile
import org.pac4j.oauth.client.BaseOAuthClient
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

import static org.apache.commons.logging.LogFactory.getLog

/**
 * Created by anil on 22/8/14.
 */
class CustomRestOauthService extends RestOauthService {
    OAuthLoginService OAuthLoginService
    private static final log = getLog(this)

    String storeAuthentication(String provider, WebContext context) {
        BaseOAuthClient client = getClient(provider)
        Credentials credentials = client.getCredentials context

        log.debug "Querying provider to fetch User ID"
        CommonProfile profile = client.getUserProfile credentials, null
        log.debug "User's ID: ${profile.id}"

        def providerConfig = grailsApplication.config.grails.plugin.springsecurity.rest.oauth."${provider}"
        List defaultRoles = providerConfig.defaultRoles.collect { new SimpleGrantedAuthority(it) }
        OauthUser userDetails = oauthUserDetailsService.loadUserByUserProfile(profile, defaultRoles)

        String tokenValue = tokenGenerator.generateToken()
        log.debug "Generated REST authentication token: ${tokenValue}"

        log.debug "Storing token on the token storage"
        tokenStorageService.storeToken(tokenValue, userDetails)
        Authentication authenticationResult = new RestAuthenticationToken(userDetails, userDetails.password, userDetails.authorities, tokenValue)
//        authenticationResult.authorities=defaultRoles
//        authenticationResult.setProperty('authorities',defaultRoles)
        OAuthLoginService.registerUserIfNotExist(authenticationResult.principal.userProfile, defaultRoles)
        SecurityContextHolder.context.setAuthentication(authenticationResult)

        return tokenValue
    }
}
