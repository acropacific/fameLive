package com.famelive.admin.util

import com.famelive.common.constant.CommonConstants
import com.famelive.common.properties.MessagesFileFilter
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication

class CommonAdminMessagesUtil {
    static Properties messageSource
    DefaultGrailsApplication grailsApplication

    void initializeMessageSource(DefaultGrailsApplication grailsApplication) {
        this.grailsApplication = grailsApplication
        updateMessageProperties()
    }

    private File[] getPropertiesFiles() {
        File messagesDIR = grailsApplication.mainContext.getResource(CommonConstants.MESSAGES_PATH).getFile()
        if (messagesDIR.exists() && messagesDIR.isDirectory()) {
            return messagesDIR.listFiles(new MessagesFileFilter())
        } else {
            return new File[0]
        }
    }

    private InputStream getInputStream(File file) {
        FileInputStream fis = new FileInputStream(file)
        return (InputStream) fis
    }


    private getMapFromProperties(Properties properties) {
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : properties.keySet()) {
            map.put(key.toString(), properties.get(key).toString());
        }
        return map
    }

    private Map loadPropertiesFile(File propertiesFile) {
        Properties properties = new Properties()
        properties.load(getInputStream(propertiesFile))
        return getMapFromProperties(properties)
    }

    private Map getPropertyMap() {
        File[] propertiesFiles = getPropertiesFiles()
        Map propertyMap = [:]
        propertiesFiles.each { propertyFile ->
            if (propertyFile.exists() && propertyFile.isFile()) {
                propertyMap.putAll(loadPropertiesFile(propertyFile))
            }
        }
        return propertyMap
    }

    private void updateMessageProperties(Map map) {
        messageSource = new Properties()
        messageSource.putAll(map)
    }

    private void updateMessageProperties() {
        Map propertiesMap = getPropertyMap()
        updateMessageProperties(propertiesMap)
    }
}
