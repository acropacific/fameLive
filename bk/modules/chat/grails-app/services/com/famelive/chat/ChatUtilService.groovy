package com.famelive.chat

import com.famelive.command.DataPushCommand
import grails.transaction.Transactional
import groovy.jmx.builder.JmxBuilder

import javax.management.MBeanServerConnection
import javax.management.ObjectName

@Transactional
class ChatUtilService {
    def grailsApplication

    public Map getChatInfo(DataPushCommand dataPushCommand) {
        Map chatInfo = [:]
        chatInfo.put("message", dataPushCommand.message)
        chatInfo.put("senderName", dataPushCommand.senderName)
        chatInfo.put("channels", dataPushCommand.channels)
        return chatInfo
    }

    public Boolean pushToPubnubByJMX(Map chatInfo) {
        Boolean pushNotificationStatus = false
        try {
            sendPushToPubnubByJMX(chatInfo)
            pushNotificationStatus = true
        } catch (Exception e) {
            log.info('Exception to call pubnub by JMX call')
            e.printStackTrace()
        }
        return pushNotificationStatus
    }

    public void sendPushToPubnubByJMX(Map chatInfo) {
        Integer jmxPort = grailsApplication.config.famelive.jmx.server.port as Integer
        Object[] parameters = [chatInfo] as Object[]
        String[] signature = [Map.class.getName()] as String[]
        def connection = new JmxBuilder().client(port: jmxPort, host: 'localhost')
        connection.connect()
        MBeanServerConnection mbeans = connection.MBeanServerConnection
        ObjectName obj = new ObjectName('fameLive:service=PubnubChat,type=special')
        mbeans.invoke(obj, 'pushMsgToPubnub', parameters, signature)
        connection.close()
    }

}
