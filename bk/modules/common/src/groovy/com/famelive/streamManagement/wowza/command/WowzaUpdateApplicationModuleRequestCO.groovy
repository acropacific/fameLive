package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType
import com.famelive.streamManagement.wowza.AdvanceSetting
import com.famelive.streamManagement.wowza.WowzaModule
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 17/11/14.
 */
class WowzaUpdateApplicationModuleRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.PUT
        contentType = "application/json"
    }
    String applicationName
    List<WowzaModule> modules = []
    AdvanceSetting advanceSetting
    String newValue
    String restURI
    String version
    JSONArray saveFieldList

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/adv"
    }

    String getBody() {
        JSONObject body = new JSONObject()
        body.put("restURI", restURI)
        body.put("serverName", serverName)
        body.put("saveFieldList", saveFieldList)
        body.put("version", version)
        body.put("advancedSettings", [advanceSetting.getJSONObject()] as JSONArray)
        body.put("modules", modules*.getJSONObject() as JSONArray)
        return body.toString();
    }
}
