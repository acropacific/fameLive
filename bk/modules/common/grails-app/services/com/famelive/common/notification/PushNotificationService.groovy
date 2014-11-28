package com.famelive.common.notification

import com.famelive.common.constant.CommonConstants
import grails.transaction.Transactional
import groovy.jmx.builder.JmxBuilder

import javax.management.MBeanServerConnection
import javax.management.ObjectName

@Transactional
class PushNotificationService {

    def grailsApplication

    public Boolean sendDataToJMX(Map chatInfo) {
        Boolean pupNotificationStatus = false
        try {
            pushMessageToPubnub(chatInfo)
            pupNotificationStatus = true
        } catch (Exception e) {
            log.info("Exception to Connect Chat Module via JMX::${chatInfo}")
            e.printStackTrace()
        }
        return pupNotificationStatus
    }

    public void pushMessageToPubnub(Map chatInfo) {
        Object[] parameters = [chatInfo] as Object[]
        String[] signature = [Map.class.getName()] as String[]
        def connection = getJMXClientConnection()
        invokeJMXRemoteMethod(connection, parameters, signature)
        connection.close()
    }

    public def getJMXClientConnection() {
        Integer jmxPort = grailsApplication.config.famelive.jmx.server.port as Integer
        String jmxHost = grailsApplication.config.famelive.jmx.server.host.toString()
        def connection = new JmxBuilder().client(port: jmxPort, host: jmxHost)
        connection.connect()
        return connection
    }

    public Object invokeJMXRemoteMethod(def connection, Object[] parameters, String[] signature) {
        MBeanServerConnection mbeans = connection.MBeanServerConnection
        ObjectName obj = new ObjectName(CommonConstants.JMX_OBJECTNAME_CONSTRUCTOR_PARAMETER)
        return mbeans.invoke(obj, CommonConstants.JMX_SERVICE_METHOD_NAME, parameters, signature)
    }


}
