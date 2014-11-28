package grails.plugin.springsecurity.userdetails

import com.famelive.api.util.AuthenticationUtil
import grails.plugin.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Created by anil on 25/8/14.
 */
class CustomGormUserDetailsService extends GormUserDetailsService {
    GrailsApplication grailsApplication

    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {

        def conf = SpringSecurityUtils.securityConfig
        String userClassName = conf.userLookup.userDomainClassName
        def dc = grailsApplication.getDomainClass(userClassName)
        if (!dc) {
            throw new IllegalArgumentException("The specified user domain class '$userClassName' is not a domain class")
        }

        Class<?> User = dc.clazz

        User.withTransaction { status ->
//            def user = User.findWhere((conf.userLookup.usernamePropertyName): username)
            def user = AuthenticationUtil.fetchUserByUsername(username)
            if (!user) {
                log.warn "User not found: $username"
                throw new NoStackUsernameNotFoundException()
            }

            Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
            createUserDetails user, authorities
        }
    }

    /*protected UserDetails createUserDetails(user, Collection<GrantedAuthority> authorities) {

        def conf = SpringSecurityUtils.securityConfig

        String usernamePropertyName = conf.userLookup.usernamePropertyName
        String passwordPropertyName = conf.userLookup.passwordPropertyName
        String enabledPropertyName = conf.userLookup.enabledPropertyName
        String accountExpiredPropertyName = conf.userLookup.accountExpiredPropertyName
        String accountLockedPropertyName = conf.userLookup.accountLockedPropertyName
        String passwordExpiredPropertyName = conf.userLookup.passwordExpiredPropertyName

        String username = user."$usernamePropertyName"
        String password = user."$passwordPropertyName"
        boolean enabled = enabledPropertyName ? user."$enabledPropertyName" : true
        boolean accountExpired = accountExpiredPropertyName ? user."$accountExpiredPropertyName" : false
        boolean accountLocked = accountLockedPropertyName ? user."$accountLockedPropertyName" : false
        boolean passwordExpired = passwordExpiredPropertyName ? user."$passwordExpiredPropertyName" : false

        new GrailsUser(username, password, enabled, !accountExpired, !passwordExpired,
                !accountLocked, authorities, user.id)
    }*/

}
