package com.famelive.api

import com.famelive.common.user.Role
import com.famelive.common.user.User
import com.famelive.common.user.UserInfo
import com.famelive.common.user.UserRole
import com.famelive.common.util.FameLiveUtil
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.pac4j.oauth.profile.facebook.FacebookProfile
import org.pac4j.oauth.profile.google2.Google2Profile
import org.pac4j.oauth.profile.twitter.TwitterProfile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService

@Transactional
class OAuthLoginService {
    UserDetailsService userDetailsService
    SpringSecurityService springSecurityService
//    private static final log = getLog(this)
    AuthenticationManager authenticationManager
    def grailsApplication

    void registerUserIfNotExist(FacebookProfile facebookProfile, List<String> defaultRoles) {
        User user = User.findByFacebookUsername(facebookProfile.id.toString())
        if (user == null) {
            try {
                User.withNewTransaction {
                    user = new User(username: facebookProfile.getId(), email: facebookProfile.getEmail(), password: FameLiveUtil.getDefaultPassword()).save(flush: true)
                    for (String role in defaultRoles) {
                        // try {
                        UserRole userRole = new UserRole(user: user, role: Role.findByAuthority(role)).save(flush: true)
                        /*} catch (Exception e) {
                            log.debug("exception while assigning role to oauth user ::" + e)
                        }*/
                    }
                    UserInfo userInfo = new UserInfo(user: user, firstName: facebookProfile.firstName, middleName: facebookProfile.middleName, dob: facebookProfile.birthday, location: facebookProfile.location, gender: facebookProfile.gender).save(flush: true)
                }
            } catch (Exception e) {
                e.printStackTrace()
                log.debug("exception in registerUserIfNotExist ::" + e)
            }
        }
//        UserCache userCache = grailsApplication.mainContext.getBean("userCache");
//        userCache.removeUserFromCache(facebookProfile.getId());
//        springSecurityService.reauthenticate(facebookProfile.getId(), FameLiveUtil.getDefaultPassword())
//        springSecurityService.reauthenticate("admin", "admin")

//        SecurityContextHolder.context.setAuthentication(null)
//        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(facebookProfile.getId(), FameLiveUtil.getDefaultPassword(), defaultRoles));
//        springSecurityService?.authentication=SecurityContextHolder.context.authentication
        UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(facebookProfile.getId(), FameLiveUtil.getDefaultPassword(), defaultRoles)

        //, password: FameLiveUtil.getDefaultPassword()
//        UserDetails userDetails = userDetailsService.loadUserByUsername(facebookProfile.id)
//        RestAuthenticationToken authentication = new RestAuthenticationToken(userDetails.username, FameLiveUtil.getDefaultPassword(), userDetails.authorities, token)
        ProviderManager pm = grailsApplication.mainContext.getBean("authenticationManager")
//        ProviderManager pm =(ProviderManager)WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("authenticationManager");
        pm.setEraseCredentialsAfterAuthentication(false)
//        springSecurityService.reauthenticate(facebookProfile.getId(),FameLiveUtil.getDefaultPassword())
        Authentication authentication = new UsernamePasswordAuthenticationToken(facebookProfile.getId(), FameLiveUtil.getDefaultPassword(), defaultRoles)
        Authentication auth = pm.authenticate(authentication)
//        authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
//        new new PrincipalSpringSecurityUserToken()
        SecurityContextHolder.context.setAuthentication(auth)

        println 'register facebookProfile :' + facebookProfile
    }

    void registerUserIfNotExist(Google2Profile google2Profile, List<String> defaultRoles) {
        println 'register google2Profile :' + google2Profile
    }

    void registerUserIfNotExist(TwitterProfile twitterProfile, List<String> defaultRoles) {
        println 'register twitterProfile :' + twitterProfile
    }

    void registerUserIfNotExist(Object otherProfile, List<String> defaultRoles) throws IllegalArgumentException {
        println 'register otherProfile :' + otherProfile
        throw new IllegalArgumentException("Login provider not supported.")
    }

}
