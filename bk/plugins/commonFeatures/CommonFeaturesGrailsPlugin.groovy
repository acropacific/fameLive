import com.grails.plugin.commonFeatures.CustomRequestFilter
import com.grails.plugin.commonFeatures.DomainClassEnhancer
import com.grails.plugin.commonFeatures.MetaClassHelper
import com.grails.plugin.commonFeatures.PersistenceContextExecutorWrapper
import org.springframework.web.filter.DelegatingFilterProxy

import java.util.concurrent.Executors

class CommonFeaturesGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "2.0 > *"
    def dependsOn = ['console': '1.0.1 > *']
    def loadAfter = ['console'];
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Common Features Plugin" // Headline display name of the plugin
    def author = "Bhagwat Kumar"
    def authorEmail = "bhagwat@intelligrape.com"
    def description = '''\
Commonly used tasks are wrapped in this plugin like meta methods, filters etc
'''
    def documentation = "http://grails.org/plugin/common-features"


    def doWithWebDescriptor = { xml ->
        def contextParam = xml.'context-param'

        contextParam[contextParam.size() - 1] + {
            'filter' {
                'filter-name'('customRequestFilter')
                'filter-class'(DelegatingFilterProxy.name)
            }
        }

        // and the filter-mapping(s) right after the last filter
        def filter = xml.'filter'

        filter[filter.size() - 1] + {
            'filter-mapping' {
                'filter-name'('customRequestFilter')
                'url-pattern'('/webService/*')
                'url-pattern'('/api/*')
            }
        }


    }

    def doWithSpring = {
        executorService(PersistenceContextExecutorWrapper) { bean ->
            bean.destroyMethod = 'destroy'
            persistenceInterceptor = ref("persistenceInterceptor")
            executor = Executors.newCachedThreadPool()
        }

        customRequestFilter(CustomRequestFilter)
    }

    def doWithDynamicMethods = { ctx ->
        MetaClassHelper.enrichClasses(application)
        DomainClassEnhancer domainClassEnhancer = new DomainClassEnhancer(application, ctx)
        domainClassEnhancer.injectMetaMethods()
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def onShutdown = { event ->
    }
}
