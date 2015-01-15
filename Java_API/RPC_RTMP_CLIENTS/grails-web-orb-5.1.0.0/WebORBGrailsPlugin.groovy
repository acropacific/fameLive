class WebORBGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Midnight Coders"
    def authorEmail = "info@themidnightcoders.com"
    def title = "WebORB plugin for Grails"
    def description = ""

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/weborb"

    def doWithWebDescriptor = { xml ->
	
	    def contextparam = xml.'context-param'[0]
        contextparam + {
            'context-param' {
            	'param-name'('globalScope')
            	'param-value'('default')
            }
            'context-param' {
            	'param-name'('parentContextKey')
            	'param-value'('default.context')
            }
        }

        def listeners = xml.listener
        listeners[listeners.size() - 1] + {
			listener {
                'listener-class'("weborb.ORBServletContextListener")
            }
			listener {
	            'listener-class'('org.springframework.web.util.Log4jConfigListener')
	        }
        }

		def servlets = xml.servlet
        servlets[servlets.size() - 1] + {
            servlet {
                'servlet-name'("weborb")
                'servlet-class'("weborb.ORBServlet")
                'load-on-startup'("1")
            }

            servlet {
                'servlet-name'("download")
                'servlet-class'("weborb.DownloadServlet")
                'load-on-startup'("1")
            }
 			servlet {
            	'servlet-name'('gateway')
            	'servlet-class'('org.red5.server.net.servlet.AMFGatewayServlet')
            	'load-on-startup'(2)
            }
            servlet {
            	'servlet-name'('rtmpt')
            	'servlet-class'('org.red5.server.net.rtmpt.RTMPTServlet')
            	'load-on-startup'(3)
            }
		}
		
		def servletMappings = xml.'servlet-mapping'
        servletMappings[servletMappings.size() - 1] + {
			'servlet-mapping' {
                'servlet-name'("weborb")
                'url-pattern'("*.wo")
            }
			'servlet-mapping' {
                'servlet-name'("download")
                'url-pattern'("/codegen.wo")
            }
            'servlet-mapping' {
            	'servlet-name'('gateway')
            	'url-pattern'('/gateway')
            }
            'servlet-mapping' {
            	'servlet-name'('rtmpt')
            	'url-pattern'('/open/*')
            }
            'servlet-mapping' {
            	'servlet-name'('rtmpt')
            	'url-pattern'('/idle/*')
            }
            'servlet-mapping' {
            	'servlet-name'('rtmpt')
            	'url-pattern'('/send/*')
            }

            'servlet-mapping' {
            	'servlet-name'('rtmpt')
            	'url-pattern'('/close/*')
            }
		}
		
		//add security constraints to the mappings
        servletMappings + {
            'security-constraint' {
                'web-resource-collection'{
                    'web-resource-name'('Forbidden')
                    'url-pattern'('/WEB-INF/*')
                }
                'auth-constraint'()
            }
            'security-constraint' {
                'web-resource-collection'{
                    'web-resource-name'('Forbidden')
                    'url-pattern'('/persistence/*')
                }
                'auth-constraint'()
            }
            'security-constraint' {
                'web-resource-collection' {
                    'web-resource-name'('Forbidden')
                    'url-pattern'('/streams/*')
                }
                'auth-constraint'()
            }
		}
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
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
}
