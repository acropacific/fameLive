package com.famelive.admin.util

import com.famelive.admin.constant.AdminConstants
import com.famelive.common.properties.MessagesFileFilter
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication

class AdminMessagesUtil {

    static Properties messageSource
    DefaultGrailsApplication grailsApplication

    void initializeMessageSource(DefaultGrailsApplication grailsApplication) {
        this.grailsApplication = grailsApplication
        updateMessageProperties()
    }

    private File[] getPropertiesFiles() {
        File messagesDIR = grailsApplication.mainContext.getResource(AdminConstants.Messages_PATH).getFile()
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

    private Map getAdminPropertyMap() {
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
        new CommonAdminMessagesUtil().initializeMessageSource(grailsApplication)
        Properties commonMessageSource = CommonAdminMessagesUtil.messageSource
        Map commonPropertiesMap
        if (commonMessageSource) {
            commonPropertiesMap = getMapFromProperties(CommonAdminMessagesUtil.messageSource)
        } else {
            commonPropertiesMap = [:]
        }
        return commonPropertiesMap
    }

    private void updateMessageProperties(Map commonPropertyMap, Map adminPropertyMap) {
        messageSource = new Properties()
        Map propertyMap = [:]
        propertyMap.putAll(commonPropertyMap)
        propertyMap.putAll(adminPropertyMap)
        messageSource.putAll(propertyMap)
    }

    private void updateMessageProperties() {
        Map adminPropertyMap = getAdminPropertyMap()
        Map commonPropertyMap = getCommonPropertyMap()
        updateMessageProperties(commonPropertyMap, adminPropertyMap)
    }
}
