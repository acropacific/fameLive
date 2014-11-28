package org.springframework.security.authentication

import com.famelive.api.util.AuthenticationUtil
import com.famelive.common.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl

/**
 * Created by anil on 23/8/14.
 *
 * Reference : http://gradlesummit.com/blog/burt_beckwith
 * Reference : http://thandomafela.wordpress.com/2013/08/26/custom-authentication-in-springsecurity-grails/
 */
class CustomAuthenticationProvider implements AuthenticationProvider {
    SpringSecurityService springSecurityService

    @Override
    Authentication authenticate(Authentication customAuth) {
//        User.withTransaction { status ->
        User user = AuthenticationUtil.fetchUserByUsername(customAuth.principal)

        if (user) {
            if (springSecurityService.getPasswordEncoder().isPasswordValid(user?.password, customAuth.credentials, null)) {
//                    user?.password == springSecurityService.encodePassword(customAuth.credentials,null) || ldapAuthMethodHere(customAuth)
                Collection<GrantedAuthority> authorities = user.authorities.collect {
                    new GrantedAuthorityImpl(it.authority)
                };
                def userDetails = new GrailsUser(user.username, user.password, true, true, true, true, authorities, user.id);

                def token = new UsernamePasswordAuthenticationToken(userDetails, user.password, userDetails?.authorities);

                token.details = customAuth.details;
                return token
            } else {
                throw new BadCredentialsException("Log in failed - identity could not be verified");
            }

        } else {
            return null
        }
//        }
    }

    @Override
    boolean supports(Class<? extends Object> aClass) {
        return true
    }

    public boolean ldapAuthMethodHere(Authentication customAuth) {
        //ldap code
    }
}
