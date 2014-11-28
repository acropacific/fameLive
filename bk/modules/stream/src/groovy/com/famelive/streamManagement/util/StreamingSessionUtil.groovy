package com.famelive.streamManagement.util

import com.famelive.common.user.Role
import com.famelive.common.user.User
import grails.plugin.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import org.springframework.security.core.Authentication

/**
 * Created by anil on 21/11/14.
 */
class StreamingSessionUtil {
    static SpringSecurityService springSecurityService

    public static User getCurrentUser() {
        GrailsHibernateUtil.unwrapIfProxy(User.get(springSecurityService?.currentUser?.id as Long))
    }

    public static Authentication getCurrentUserAuthentication() {
        springSecurityService.authentication
    }

    static Role fetchCurrentUserRole() {
        currentUser?.authorities?.first()
    }

    static List<Role> fetchCurrentUserRoles() {
        currentUser?.authorities
    }
}
