import com.famelive.api.APISecurityService
import com.famelive.api.security.APIValidationFilter
import com.famelive.api.util.ApiSessionUtils
import com.odobo.grails.plugin.springsecurity.rest.CustomRestAuthenticationFailureHandler
import com.odobo.grails.plugin.springsecurity.rest.CustomRestAuthenticationSuccessHandler
import com.odobo.grails.plugin.springsecurity.rest.CustomRestOauthService
import com.odobo.grails.plugin.springsecurity.rest.RestAuthenticationFilter
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.CustomGormUserDetailsService
import grails.util.GrailsUtil
import org.springframework.security.authentication.dao.CustomDaoAuthenticationProvider

class ApiGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.4 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Api Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/api"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before


    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
        def conf = SpringSecurityUtils.securityConfig

        restOauthService(CustomRestOauthService) {
            tokenGenerator = ref('tokenGenerator')
            tokenStorageService = ref('tokenStorageService')
            userDetailsService = ref('userDetailsService')
            grailsApplication = ref('grailsApplication')
            grailsLinkGenerator = ref('grailsLinkGenerator')
            oauthUserDetailsService = ref('oauthUserDetailsService')
            OAuthLoginService = ref('OAuthLoginService')
        }

        daoAuthenticationProvider(CustomDaoAuthenticationProvider) {
            userDetailsService = ref('userDetailsService')
            passwordEncoder = ref('passwordEncoder')
            userCache = ref('userCache')
            saltSource = ref('saltSource')
            preAuthenticationChecks = ref('preAuthenticationChecks')
            postAuthenticationChecks = ref('postAuthenticationChecks')
            authoritiesMapper = ref('authoritiesMapper')
            hideUserNotFoundExceptions = conf.dao.hideUserNotFoundExceptions // true
            grailsApplication = ref('grailsApplication')
        }



        userDetailsService(CustomGormUserDetailsService) {
            grailsApplication = ref('grailsApplication')
        }

        restAPIValidationFilter(APIValidationFilter) {
            grailsApplication = ref('grailsApplication')
            authenticationManager = ref("authenticationManager")
            rememberMeServices = ref("rememberMeServices")
            springSecurityService = ref("springSecurityService")
        }

        SpringSecurityUtils.loadSecondaryConfig 'DefaultRestSecurityConfig'
        conf = SpringSecurityUtils.securityConfig

        customRestAuthenticationSuccessHandler(CustomRestAuthenticationSuccessHandler) {
            renderer = ref('restAuthenticationTokenJsonRenderer')
        }

        customRestAuthenticationFailureHandler(CustomRestAuthenticationFailureHandler) {
        }

        restAuthenticationFilter(RestAuthenticationFilter) {
            authenticationManager = ref('authenticationManager')
            authenticationSuccessHandler = ref('customRestAuthenticationSuccessHandler')
            authenticationFailureHandler = ref('customRestAuthenticationFailureHandler')
            authenticationDetailsSource = ref('authenticationDetailsSource')
            credentialsExtractor = ref('credentialsExtractor')
            endpointUrl = conf.rest.login.endpointUrl
            tokenGenerator = ref('tokenGenerator')
            tokenStorageService = ref('tokenStorageService')
        }

        apiSessionUtils(ApiSessionUtils) {
            springSecurityService = ref('springSecurityService')
        }

        apiSecurityService(APISecurityService) {
            userDetailsService = ref('userDetailsService')
            tokenGenerator = ref('tokenGenerator')
            tokenStorageService = ref('tokenStorageService')
            authenticationDetailsSource = ref('authenticationDetailsSource')
            authenticationManager = ref('authenticationManager')
            springSecurityService = ref('springSecurityService')
        }

        def config = application.config
        GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)
        config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('ApiConfig')))

        //TODO declare customAuthenticationProvider inside providerNames only for API module
        /*customAuthenticationProvider(CustomAuthenticationProvider) {
            springSecurityService = ref('springSecurityService')
        }*/
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
