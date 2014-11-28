import com.famelive.common.CommonBootStrapService

class BootStrap {
    CommonBootStrapService commonBootStrapService
//    MandrillMailService mailService

    def init = {
//        mailService.initializeMailConfig()
        /*servletContext ->
           commonBootStrapService.bootstrapUserRoles()
           commonBootStrapService.bootstrapSuperAdmin()
           commonBootStrapService.bootstrapUsers()
           commonBootStrapService.bootstrapWowzaChannels()
   //        grails.plugin.springsecurity.SpringSecurityUtils.registerFilter('restAPIValidationFilter',0)
   //        SpringSecurityUtils.clientRegisterFilter('restAPIValidationFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 0)
           SpringSecurityUtils.clientRegisterFilter('restAPIValidationFilter', 0)
   */
//        XML.registerObjectMarshaller(new org.codehaus.groovy.grails.web.converters.marshaller.xml.InstanceMethodBasedMarshaller())
//        JSON.registerObjectMarshaller(new org.codehaus.groovy.grails.web.converters.marshaller.json.InstanceMethodBasedMarshaller())


    }
    def destroy = {
    }
}
