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

rabbitmq {
    connectionfactory {
        username = 'guest'
        password = 'guest'
        hostname = 'localhost'
        concurrentConsumers = 5
    }

    queues = {
        fameliveQueue autoDelete: false, durable: true, exclusive: false
    }
}

//SANJEEV
//famelive.pubnub.publishKey = "pub-c-1f55b789-3aa7-4d29-b52b-254b254566f7"
//famelive.pubnub.subscribeKey = "sub-c-6be17844-4ecb-11e4-8dac-02ee2ddab7fe"

//VISHAL
famelive.pubnub.publishKey = "pub-c-5efb6ba8-1119-4fd6-b0c0-6d7b70fab81a"
famelive.pubnub.subscribeKey = "sub-c-0b6ad250-6337-11e4-8b69-02ee2ddab7fe"
famelive.jmx.server.port = 10004