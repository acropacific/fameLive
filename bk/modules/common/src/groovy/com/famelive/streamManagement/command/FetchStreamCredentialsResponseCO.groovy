package com.famelive.streamManagement.command

import com.famelive.streamManagement.wowza.Publisher
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 2/11/14.
 */
class FetchStreamCredentialsResponseCO extends ResponseCO {
    Publisher publisher

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("success", success)
        jsonObject.put("message", message)
        if (publisher) {
            jsonObject.put("publisher", publisher.toJson())
        }
        return jsonObject
    }
}
