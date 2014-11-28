package com.famelive.streamManagement.wowza

import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 17/11/14.
 */
class AdvanceSetting {
    String tagName = "AdvancedSetting"
    String enabled
    String canRemove
    String name
    String defaultValue
    String type
    String sectionName
    String documented
    String value
    String section = "/Root/Application"

    JSONObject getJSONObject() {
        JSONObject response = new JSONObject()
        response.put("enabled", enabled)
        response.put("name", name)
        response.put("canRemove", canRemove)
        response.put("defaultValue", defaultValue)
        response.put("type", type)
        response.put("sectionName", sectionName)
        response.put("section", section)
        response.put("documented", documented)
        response.put("value", value)
        return response
    }
}
