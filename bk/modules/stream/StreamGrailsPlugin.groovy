import com.famelive.streamManagement.filter.StreamingSecurityFilter
import com.famelive.streamManagement.util.StreamingSessionUtil
import grails.util.GrailsUtil

class StreamGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.4 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Stream Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/streamManagement"

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
        //filter.size() - 1
        /*def firstFilter = xml.'filter'[0]
        firstFilter + {
            'filter-name'('streamCommonFilter')
            'filter-class'('com.famelive.streamManagement.filter.StreamCommonFilter')
        }
        def firstFilterMapping = xml.'filter-mapping'[0]
        firstFilterMapping + {
            'filter-name'('streamCommonFilter')
            'url-pattern'('*//*')

        }*/

        /*def contextParams = xml.'context-param'
        contextParams[contextParams.size() - 1] + {
            'filter' {
                'filter-name'('streamCommonFilter')
                'filter-class'('com.famelive.streamManagement.filter.StreamCommonFilter')
            }
            'filter-mapping' {
                'filter-name'('streamCommonFilter')
                'url-pattern'('')
            }
        }*/


    }

    def doWithSpring = {
        def config = application.config
        GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)
        config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('StreamConfig')))

        /* ***** Only use of this filter is to make the request re-readable *******

        streamCommonFilter(StreamCommonFilter) {
        }*/

        streamingSecurityFilter(StreamingSecurityFilter) {
            grailsApplication = ref('grailsApplication')
            authenticationManager = ref("authenticationManager")
            rememberMeServices = ref("rememberMeServices")
            springSecurityService = ref("springSecurityService")
        }

        streamingSessionUtils(StreamingSessionUtil) {
            springSecurityService = ref('springSecurityService')
        }


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
        application {
            grails {
                plugin {
                    springsecurity {
                        logout {
                            afterLogoutUrl = '/login/index'
                        }
                        successHandler {
                            defaultTargetUrl = '/admin/index'

                        }
                    }
                }
            }
        }
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
