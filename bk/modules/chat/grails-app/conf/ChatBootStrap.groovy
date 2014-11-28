import groovy.jmx.builder.JmxBuilder

import java.rmi.registry.LocateRegistry

class ChatBootStrap {

    def grailsApplication
    def init = { servletContext ->
        Integer rmiPort = grailsApplication.config.famelive.jmx.server.port as Integer
        Properties systemProperties = System.getProperties()
        systemProperties.setProperty('java.rmi.server.hostname', 'localhost')
        systemProperties.setProperty('com.sun.management.jmxremote.port', 'rmiPort')
        systemProperties.setProperty('com.sun.management.jmxremote.authenticate', 'false')
        systemProperties.setProperty('com.sun.management.jmxremote.ssl', 'false')
        LocateRegistry.createRegistry(rmiPort)
        new JmxBuilder().connectorServer(port: rmiPort).start()
        println '*************JMX Related System Configuration is Done***************'
    }

    def destroy = {
    }
}
