/**
 * Created by anil on 16/10/14.
 */


famelive.event.securityToken.beforeStartBufferTime = 5 //In minutes


grails.plugin.springsecurity.cas.active = true
grails.plugin.springsecurity.cas.serverUrlEncoding = 'UTF-8'
grails.plugin.springsecurity.cas.sendRenew = false //if true, ticket validation will only succeed if it. was issued from a login form, but will fail if it was issued from a single sign-on session
grails.plugin.springsecurity.cas.key = 'grails-spring-security-cas' //used by CasAuthenticationProvider  to identify tokens it previously authenticated
grails.plugin.springsecurity.cas.artifactParameter = 'ticket'
grails.plugin.springsecurity.cas.serviceParameter = 'service'
grails.plugin.springsecurity.cas.filterProcessesUrl = '/j_spring_cas_security_check' //the URL that the filter intercepts for login.
grails.plugin.springsecurity.cas.loginUri = '/login'
grails.plugin.springsecurity.cas.serviceUrl = 'http://localhost:8085/fameLive/j_spring_cas_security_check'
grails.plugin.springsecurity.cas.serverUrlPrefix = 'https://localhost:8080/cas'
grails.plugin.springsecurity.cas.proxyCallbackUrl = 'http://localhost:8085/fameLive/secure/receptor'
grails.plugin.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'
grails.plugin.springsecurity.cas.useSingleSignout = false
grails.plugin.springsecurity.logout.afterLogoutUrl = 'https://localhost:8080/cas/logout?.url=http://localhost:8085/fameLive/'