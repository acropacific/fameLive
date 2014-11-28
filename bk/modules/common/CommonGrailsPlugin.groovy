import com.famelive.common.callBroker.APIRequestCallBroker
import com.famelive.common.callBroker.RequestCallBrokerAdapter
import com.famelive.common.callBroker.WowzaRequestCallBroker
import com.famelive.common.constant.CommonConstants
import com.famelive.common.slotManagement.rule.*
import grails.util.Environment
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication

class CommonGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Common Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/common"

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
//        mergeConfig(application)
        // TODO Implement runtime spring config (optional)
        def config = application.config
        GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)
        config.merge(new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass('CommonConfig')))


        checkCreateSlotAvailabilityRule(CheckSlotAvailabilityRule) {
            maxNumberOfChannels = config.commonConfig.concurrent.channels as Integer
            grailsApplication = ref('grailsApplication')
            slotService = ref('slotService')
        }
        checkEditSlotAvailabilityRule(CheckSlotAvailabilityRule) {
            maxNumberOfChannels = (config.commonConfig.concurrent.channels as Integer) + 1
            grailsApplication = ref('grailsApplication')
            slotService = ref('slotService')
        }
        checkPublishedGenreRule(CheckPublishedGenreRule) {
        }
        checkConflictUserCreateEventRule(CheckConflictUserEventRule) {
            maxCount = 0
        }

        checkConflictUserEditEventRule(CheckConflictUserEventRule) {
            maxCount = 1
        }

        checkSlotAvailabilityRuleEngine(RuleEngine) {
            grailsApplication = ref('grailsApplication')
            rules = [ref('checkCreateSlotAvailabilityRule')] as List<Rule>
        }
        createEventRuleEngine(RuleEngine) {
            grailsApplication = ref('grailsApplication')
            rules = [ref('checkCreateSlotAvailabilityRule'), ref('checkPublishedGenreRule'), ref('checkConflictUserCreateEventRule')] as List<Rule>
        }
        editEventRuleEngine(RuleEngine) {
            grailsApplication = ref('grailsApplication')
            rules = [ref('checkEditSlotAvailabilityRule'), ref('checkPublishedGenreRule'), ref('checkConflictUserEditEventRule')] as List<Rule>
        }

        // Related to streaming module

        requestCallBrokerAdapter(RequestCallBrokerAdapter) {
            grailsApplication = application
        }

//        CallBrokerType.API_CALL_BROKER.getValue()
        wowzaCallBroker(WowzaRequestCallBroker) {

        }

//        CallBrokerType.WOWZA_CALL_BROKER.getValue()
        apiCallBroker(APIRequestCallBroker) {
            url = CommonConstants.DEFAULT_API_CALL_BROKER_URL
            userAgent = CommonConstants.DEFAULT_API_CALL_BROKER_USER_AGENT
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
//        this.mergeConfig(application)
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    def doWithConfig = { config ->
    }

    private void mergeConfig(GrailsApplication app) {
        ConfigObject currentConfig = app.config.grails.commonConfig
        ConfigSlurper slurper = new ConfigSlurper(Environment.getCurrent().getName());
        ConfigObject secondaryConfig = slurper.parse(app.classLoader.loadClass("CommonConfig"))

        ConfigObject config = new ConfigObject();
        config.putAll(secondaryConfig.commonConfig.merge(currentConfig))

        app.config.grails.commonConfig = config;
    }
}
