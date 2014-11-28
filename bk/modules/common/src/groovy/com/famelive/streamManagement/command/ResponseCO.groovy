package com.famelive.streamManagement.command

import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 1/11/14.
 */
class ResponseCO {
    boolean success
    String message

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("success", success)
        jsonObject.put("message", message)
        return jsonObject
    }
}
