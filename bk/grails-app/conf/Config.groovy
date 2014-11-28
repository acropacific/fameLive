// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

//grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy",
//                             "file:${userHome}/${appName}-${grails.util.Environment.current.name}-config.properties"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      form         : 'application/x-www-form-urlencoded',
                      html         : ['text/html', 'application/xhtml+xml'],
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

cloudinary.config = [
        cloud_name     : "famelive",
        api_key        : "195657926447579",
        api_secret     : "mWBJAvhhbKWPCcB-R3dmKXVOdTA",
        folder         : 'dev',
        mimeType       : 'jpg',
        userImageSize  : 500,
        eventPosterSize: 5000
]

environments {
    development {
        grails.logging.jul.usebridge = true
        cloudinary.config['folder'] = 'dev'
        cloudinary.config['userImageFolder'] = 'userImages'
        cloudinary.config['eventImageFolder'] = 'eventImages'
        cloudinary.config['defaultUserImage'] = 'default/user.png'
        cloudinary.config['defaultEventImage'] = 'default/event.jpg'

        famelive.adminPanel.homepage.url = 'http://localhost:8082/fameLive'
        famelive.streamingAdminPanel.homepage.url = 'http://localhost:8085/fameLive'
        famelive.api.homepage.url = 'http://localhost:8081/fameLive'
        famelive.web.homepage.url = 'http://localhost:8084/fameLive'
        famelive.social.homepage.url = 'http://localhost:8083/fameLive'
        famelive.chat.homepage.url = 'http://localhost:8082/fameLive'
    }
    qa {
        cloudinary.config['folder'] = 'qa'
        cloudinary.config['userImageFolder'] = 'userImages'
        cloudinary.config['eventImageFolder'] = 'eventImages'
        cloudinary.config['defaultUserImage'] = 'default/user.png'
        cloudinary.config['defaultEventImage'] = 'default/event.jpg'

        famelive.adminPanel.homepage.url = 'http://famelive-admin.qa3.intelligrape.net'
        famelive.streamingAdminPanel.homepage.url = 'http://famelive-streams.qa3.intelligrape.net'
        famelive.api.homepage.url = 'http://famelive-api.qa3.intelligrape.net'
        famelive.web.homepage.url = 'http://famelive-web.qa3.intelligrape.net'
        famelive.social.homepage.url = 'http://famelive-social.qa3.intelligrape.net'
        famelive.chat.homepage.url = 'http://famelive-chat.qa3.intelligrape.net'
    }
    production {
        grails.logging.jul.usebridge = false
        cloudinary.config['folder'] = 'prod'
        cloudinary.config['userImageFolder'] = 'userImages'
        cloudinary.config['eventImageFolder'] = 'eventImages'
        cloudinary.config['defaultUserImage'] = 'default/user.png'
        cloudinary.config['defaultEventImage'] = 'default/event.jpg'
        // TODO: grails.serverURL = "http://www.changeme.com"

        famelive.adminPanel.homepage.url = 'http://famelive-admin.qa3.intelligrape.net'
        famelive.streamingAdminPanel.homepage.url = 'http://famelive-streams.qa3.intelligrape.net'
        famelive.api.homepage.url = 'http://famelive-api.qa3.intelligrape.net'
        famelive.web.homepage.url = 'http://famelive-web.qa3.intelligrape.net'
        famelive.social.homepage.url = 'http://famelive-social.qa3.intelligrape.net'
        famelive.chat.homepage.url = 'http://famelive-chat.qa3.intelligrape.net'
    }

}

cloudinary.config['baseUrl'] = "http://res.cloudinary.com/${cloudinary.config['cloud_name']}/image/upload/"

// log4j configuration
/*
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
}
*/

def applicationName = appName
log4j = {

    // Example of changing the log pattern for the default console appender:
    //
    def catalinaBase = System.properties.getProperty('catalina.base')
    if (!catalinaBase) catalinaBase = '.'   // just in case
    def logDirectory = "${catalinaBase}/logs"

    environments {
        production {
            appenders {
                rollingFile name: 'allLogs', maxFileSize: '50MB', maxBackupIndex: 100, file: "${logDirectory}/${applicationName}_app.log", {
                    layout:
                    pattern(
                            conversionPattern: '%%d{yyyy-MM-dd HH:mm:ss.SSS} | %p | %c | %t | %m | %x%n'
                    )
                }
                rollingFile name: 'fameLive', maxFileSize: '50MB', maxBackupIndex: 100, file: "${logDirectory}/${applicationName}_FameLive_app.log", {
                    layout:
                    pattern(
                            conversionPattern: '%%d{yyyy-MM-dd HH:mm:ss.SSS} | %p | %c | %t | %m | %x%n'
                    )
                }
                console name: 'stdout', layout: pattern(conversionPattern: '%-5p %d{dd-MM-yyyy HH:mm:ss} -> %m%n')
            }

            error allLogs: ['org.codehaus.groovy.grails.web.servlet',  //  controllers
                            'org.codehaus.groovy.grails.web.pages', //  GSP
                            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
                            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
                            'org.codehaus.groovy.grails.web.mapping', // URL mapping
                            'org.codehaus.groovy.grails.commons', // core / classloading
                            'org.codehaus.groovy.grails.plugins', // plugins
                            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
                            'org.springframework',
                            'org.hibernate',
                            'net.sf.ehcache.hibernate'
            ]

            debug allLogs: ['grails.app',
                            'grails.app.filters'
            ]

            warn allLogs: ['org.mortbay.log']

            debug mediaIQ: ['com.famelive', 'org.springframework.security.acls']
        }
        development {
            appenders {
                console name: 'stdout', layout: pattern(conversionPattern: '%-5p %d{dd-MM-yyyy HH:mm:ss} -> %m%n')
            }
            debug console: ['com.famelive', 'org.springframework.security.acls'/*, 'grails.plugins.springsecurity', 'org.codehaus.groovy.grails.plugins.springsecurity', 'org.springframework.security', 'org.jasig.cas.client'*/]
        }
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.auth.loginFormUrl = '/login/index'
//grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/login/index'
grails.plugin.springsecurity.logout.postOnly = false
//TODO declare customAuthenticationProvider inside providerNames only for API module
grails.plugin.springsecurity.providerNames = [/*'customAuthenticationProvider',*/ 'daoAuthenticationProvider', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.famelive.common.user.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.famelive.common.user.UserRole'
grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugin.springsecurity.authority.className = 'com.famelive.common.user.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        '/'              : ['permitAll'],
        '/player.html'   : ['permitAll'],
        '/player1.html': ['permitAll'],
        '/player.gsp'  : ['permitAll'],
        '/index'         : ['permitAll'],
        '/console/**'    : ['permitAll'],
        '/index.gsp'     : ['permitAll'],
        '/**/assets/**': ['permitAll'],
        '/**/js/**'      : ['permitAll'],
        '/**/jwplayer/**': ['permitAll'],
        '/**/pubnub/**'  : ['permitAll'],
        '/**/css/**'     : ['permitAll'],
        '/**/images/**'  : ['permitAll'],
        '/**/favicon.ico' : ['permitAll'],
        '/secure/receptor': ['IS_AUTHENTICATED_ANONYMOUSLY'],  // <- allows CAS to contact the receptor
]

grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.enableAnonymousAccess = true


grails.plugin.springsecurity.rememberMe.persistent = true
grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'com.famelive.common.user.PersistentLogin'

grails.plugin.springsecurity.filterChain.chainMap = [

//        '/api/auth/**': 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor',  // Stateless chain
'/api/auth/**': 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter',  // Stateless chain
'/api/**'     : 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter',  // Stateless chain
'/**'         : 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'                                          // Traditional chain
]

// fameLive admin account on google => username :admn.famelive@gmail.com, pwd :System123#


grails.plugin.springsecurity.rest.login.active = true
grails.plugin.springsecurity.rest.login.endpointUrl = '/api/login'
//grails.plugin.zz.rest.login.endpointUrl = '/api/login'
grails.plugin.springsecurity.rest.login.usernamePropertyName = 'email'
grails.plugin.springsecurity.rest.login.passwordPropertyName = 'password'
//grails.plugin.springsecurity.rest.login.apiVersionPropertyName = 'apiVersion'
//grails.plugin.springsecurity.rest.login.appKeyPropertyName = 'appKey'
grails.plugin.springsecurity.rest.login.failureStatusCode = HttpServletResponse.SC_UNAUTHORIZED    //401
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.useRequestParamsCredentials = false
grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.useMemcached = false
grails.plugin.springsecurity.rest.token.storage.useGrailsCache = false
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'com.famelive.api.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'tokenValue'
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'email'
grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName = 'email'
grails.plugin.springsecurity.rest.token.rendering.tokenPropertyName = 'access_token'
grails.plugin.springsecurity.rest.token.rendering.authoritiesPropertyName = 'roles'


grails.plugin.springsecurity.rest.oauth.frontendCallbackUrl = { String tokenValue -> "http://127.0.0.1:8080/fameLive/auth/authenticate?token=${tokenValue}" }

//Facebook OAuth configuration
grails.plugin.springsecurity.rest.oauth.facebook.key = "805047946183778"
grails.plugin.springsecurity.rest.oauth.facebook.secret = "a1edb6323c5e1504b9002c0635da6579"
grails.plugin.springsecurity.rest.oauth.facebook.client = org.pac4j.oauth.client.FacebookClient
grails.plugin.springsecurity.rest.oauth.facebook.scope = 'email' //'email,user_location'
//grails.plugin.springsecurity.rest.oauth.facebook.fields = 'id,name,first_name,middle_name,last_name,username'
grails.plugin.springsecurity.rest.oauth.facebook.defaultRoles = ['ROLE_SUPER_ADMIN', 'ROLE_ADMIN']

//Google OAuth configuration
grails.plugin.springsecurity.rest.oauth.google.key = "527993083252-8tdeul97e7ggjj76pubmhhtd1halbp21.apps.googleusercontent.com"
grails.plugin.springsecurity.rest.oauth.google.secret = ""
grails.plugin.springsecurity.rest.oauth.google.client = org.pac4j.oauth.client.Google2Client
grails.plugin.springsecurity.rest.oauth.google.scope = org.pac4j.oauth.client.Google2Client.Google2Scope.EMAIL_AND_PROFILE
//grails.plugin.springsecurity.rest.oauth.google.fields = 'id,name,first_name,middle_name,last_name,username'
grails.plugin.springsecurity.rest.oauth.google.defaultRoles = ['ROLE_SUPER_ADMIN', 'ROLE_ADMIN']

//Twitter OAuth configuration
grails.plugin.springsecurity.rest.oauth.twitter.key = ""
grails.plugin.springsecurity.rest.oauth.twitter.secret = ""
grails.plugin.springsecurity.rest.oauth.twitter.client = org.pac4j.oauth.client.TwitterClient
grails.plugin.springsecurity.rest.oauth.twitter.defaultRoles = ['ROLE_SUPER_ADMIN', 'ROLE_ADMIN']


superAdmin.username = "admin"
superAdmin.password = "admin"
user.default.password = "igdefault"



famelive.apiVersionPropertyName = 'apiVersion'
famelive.appKeyPropertyName = 'appKey'
famelive.supported.api.versions = ['1.0', '2.0', '3.0']
famelive.supported.api.appKey = 'myAppKey'

grails.gsp.enable.reload = true

//Mailing configuration

/* Mandrill specific mail configuration , do not make any changes */
grails.mail.transport.protocol = "smtp"
grails {
    mail {
        host = "smtp.mandrillapp.com"
        port = 587 //25
        authentication = "login"
        username = "admn.famelive@gmail.com"
        password = "7dqZF1M5VEsbBcyEgikZ9A"
        props = ["mail.smtp.auth"     : "true",
                 "mail.smtp.host"     : "smtp.mandrillapp.com",
                 "mail.smtp.port"     : "587",
                 "mail.smtp.submitter": "admn.famelive@gmail.com"]
    }
}

/* Mandrill specific mail configuration , do not make any changes */

grails.mail.default.from = "admn.famelive@gmail.com"
grails.mail.disabled = false

/*grails {
    mail {
        host = "smtp.gmail.com"
        port = 465 //587
        username = "admn.famelive@gmail.com"
        password = "System123#"
        props = ["mail.smtp.auth":"true",
                 "mail.smtp.socketFactory.port":"465",
                 "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback":"false",
                 "mail.smtp.starttls.enable"       : "true",
                 "mail.smtp.starttls.required"     : "false",
                 "mail.smtp.submitter":"admn.famelive@gmail.com"]

    }

grails.mail.ssl = "on"
mail.smtp.starttls.required = 'true'
grails.mail.overrideAddress="test@address.com"
grails.mail.poolSize=5
}*/

asynchronous.mail.default.attempt.interval = 300000l       // Five minutes
asynchronous.mail.default.max.attempts.count = 1
asynchronous.mail.send.repeat.interval = 60000l           // One minute
asynchronous.mail.expired.collector.repeat.interval = 607000l
asynchronous.mail.messages.at.once = 100
asynchronous.mail.send.immediately = true  // since 0.1.2
asynchronous.mail.override = false    // since 0.2.0
asynchronous.mail.clear.after.sent = false    // since 0.2.0
asynchronous.mail.disable = false    // since 0.7
asynchronous.mail.useFlushOnSave = true
asynchronous.mail.persistence.provider = 'hibernate' // Possible values are 'hibernate', 'hibernate4', 'mongodb'
asynchronous.mail.gparsPoolSize = 1
asynchronous.mail.newSessionOnImmediateSend = false

//Mail template configurations
famelive.forgotPassword.mail.template = "forgot-password-template|main"
famelive.performer.registration.mail.template = "registration-template"
famelive.viewer.registration.mail.template = "viewer-registration-template"
famelive.createEvent.mail.template = "create-event"
famelive.cancelEvent.mail.template = "cancel-event"
famelive.emailVerification.mail.template = "emailverificationmail"

social.registration.password.salt = "Fame#123"

//pushNotification
famelive.jmx.server.port = 10004
famelive.jmx.server.host = "localhost"

grails.plugin.springsecurity.password.algorithm = "MD5"
grails.plugin.springsecurity.password.hash.iterations = 1


//grails.plugin.springsecurity.rejectIfNoRule = true
//grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

grails.plugin.springsecurity.cas.active = false
//grails.plugin.springsecurity.providerNames = ['casAuthenticationProvider']
grails.plugin.springsecurity.cas.serverUrlEncoding = 'UTF-8'
//grails.plugin.springsecurity.cas.sendRenew = false //if true, ticket validation will only succeed if it. was issued from a login form, but will fail if it was issued from a single sign-on session
//grails.plugin.springsecurity.cas.key = 'grails-spring-security-cas' //used by CasAuthenticationProvider  to identify tokens it previously authenticated
//grails.plugin.springsecurity.cas.artifactParameter = 'ticket'
//grails.plugin.springsecurity.cas.serviceParameter = 'service'
//grails.plugin.springsecurity.cas.filterProcessesUrl = '/j_spring_cas_security_check' //the URL that the filter intercepts for login.
grails.plugin.springsecurity.cas.loginUri = '/login'
grails.plugin.springsecurity.cas.serviceUrl = 'http://localhost:8085/fameLive/j_spring_cas_security_check'
grails.plugin.springsecurity.cas.serverUrlPrefix = 'https://Anil-Agrawal:8443/cas'
//grails.plugin.springsecurity.cas.proxyCallbackUrl = 'http://localhost:8085/fameLive/j_spring_cas_security_proxyreceptor'
//grails.plugin.springsecurity.cas.proxyCallbackUrl = 'http://localhost:8085/fameLive/secure/receptor'
//grails.plugin.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'
//grails.plugin.springsecurity.cas.proxyReceptorUrl = 'http://localhost:8085/fameLive/secure/receptor'
//grails.plugin.springsecurity.cas.proxyReceptorUrl = '/j_spring_cas_security_proxyreceptor'
grails.plugin.springsecurity.cas.useSingleSignout = true
grails.plugin.springsecurity.logout.afterLogoutUrl = 'https://Anil-Agrawal:8443/cas/logout?url=http://localhost:8085/fameLive'
