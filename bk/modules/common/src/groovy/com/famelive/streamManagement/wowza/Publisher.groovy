package com.famelive.streamManagement.wowza

import com.famelive.common.util.FameLiveUtil
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 30/10/14.
 */
class Publisher {
    String username = FameLiveUtil.getRandomName()
    String password = FameLiveUtil.getPassword()
    String description = "temporary publisher credentials"

    JSONObject toJson() {
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("username", username.bytes.encodeBase64().toString())
        jsonObject.put("password", password.bytes.encodeBase64().toString())
        return jsonObject
    }
}
