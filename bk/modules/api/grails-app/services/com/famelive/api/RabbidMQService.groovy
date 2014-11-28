package com.famelive.api

import grails.transaction.Transactional
import groovy.jmx.builder.JmxBuilder

import javax.management.MBeanServerConnection
import javax.management.ObjectName

@Transactional
class RabbidMQService {

//    static rabbitQueue = ApiConstants.RABBIDMQ_QUEUENAME

    void handleMessage(Map chatInfo) {

        try {
            sendDataToPubnub(chatInfo)
        }
        catch (Exception e) {
            println '***************Exception to call jmx method****************'
            e.printStackTrace()
        }
    }

    public void sendDataToPubnub(Map chatInfo) {
        Object[] parameters = [chatInfo] as Object[]
        String[] signature = [Map.class.getName()] as String[]
        def connection = new JmxBuilder().client(port: 8090, host: 'localhost')
        connection.connect()
        MBeanServerConnection mbeans = connection.MBeanServerConnection
        ObjectName obj = new ObjectName('fameLive:service=PubnubChat,type=special')
        mbeans.invoke(obj, 'pushMsgToPubnub', parameters, signature)
        connection.close()
    }
}
