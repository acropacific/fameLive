package com.famelive.streamManagement.wowza

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 17/11/14.
 */
class WowzaModule {
    String tagName = "com.wowza.wms.rest.vhosts.applications.modules.ModuleConfig"
    String restURI = "com.wowza.wms.rest.vhosts.applications.modules.ModuleConfig"
    String order
    String name
    String description
    String clazz
    String serverName = "WowzaStreamingEngine"
    JSONArray saveFieldList = []
    String version = "1"

    JSONObject getJSONObject() {
        JSONObject response = new JSONObject()
        response.put("restURI", restURI)
        response.put("order", Integer.parseInt(order))
        response.put("name", name)
        response.put("saveFieldList", saveFieldList)
        response.put("serverName", serverName)
        response.put("description", description)
        response.put("version", version)
        response.put("class", clazz)
        return response
    }
}
