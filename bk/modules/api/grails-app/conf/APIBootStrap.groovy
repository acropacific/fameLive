import com.famelive.api.util.ApiMessagesUtil
import grails.plugin.springsecurity.SpringSecurityUtils

class APIBootStrap {
    def grailsApplication

    def init = { servletContext ->
        new ApiMessagesUtil().initializeMessageSource(grailsApplication)

//        grails.plugin.springsecurity.SpringSecurityUtils.registerFilter('restAPIValidationFilter',0)
//        SpringSecurityUtils.clientRegisterFilter('restAPIValidationFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 0)
        SpringSecurityUtils.clientRegisterFilter('restAPIValidationFilter', 0)
    }

    def destroy = {
    }
}
