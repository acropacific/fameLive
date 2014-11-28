// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
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
}

//grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/genre/list'
//grails.plugins.springsecurity.logout.afterLogoutUrl = '/login/index'

//grails.plugin.springsecurity.auth.loginFormUrl = '/login/index'
grails {
    plugin {
        springsecurity {
            logout {
                afterLogoutUrl = '/login/index'
            }
            successHandler {
                defaultTargetUrl = '/genre/list'

            }
        }
    }
}
//grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/login/index'
