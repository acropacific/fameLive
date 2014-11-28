package com.famelive.admin.util

import com.famelive.common.properties.MessagesFileFilter
import com.famelive.common.util.CommonMessagesUtil
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication

/**
 * Created by anil on 25/9/14.
 */
class SocialMessagesUtil {

    static Properties messageSource
    DefaultGrailsApplication grailsApplication

    void initializeMessageSource(DefaultGrailsApplication grailsApplication) {
        this.grailsApplication = grailsApplication
        updateMessageProperties()
    }

    private File[] getPropertiesFiles() {
        File messagesDIR = grailsApplication.mainContext.getResource(SocialConstants.Messages_PATH).getFile()
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

    private Map getSocialPropertyMap() {
        File[] propertiesFiles = getPropertiesFiles()
        Map propertyMap = [:]
        propertiesFiles.each { propertyFile ->
            if (propertyFile.exists() && propertyFile.isFile()) {
                propertyMap.putAll(loadPropertiesFile(propertyFile))
            }
        }
        return propertyMap
    }

    private Map getCommonPropertyMap() {
        new CommonMessagesUtil().initializeMessageSource(grailsApplication)
        Properties commonMessageSource = CommonMessagesUtil.messageSource
        Map commonPropertiesMap
        if (commonMessageSource) {
            commonPropertiesMap = getMapFromProperties(CommonMessagesUtil.messageSource)
        } else {
            commonPropertiesMap = [:]
        }
        return commonPropertiesMap
    }

    private void updateMessageProperties(Map commonPropertyMap, Map socialPropertyMap) {
        messageSource = new Properties()
        Map propertyMap = [:]
        propertyMap.putAll(commonPropertyMap)
        propertyMap.putAll(socialPropertyMap)
        messageSource.putAll(propertyMap)
    }

    private void updateMessageProperties() {
        Map socialPropertyMap = getSocialPropertyMap()
        Map commonPropertyMap = getCommonPropertyMap()
        updateMessageProperties(commonPropertyMap, socialPropertyMap)
    }
}
