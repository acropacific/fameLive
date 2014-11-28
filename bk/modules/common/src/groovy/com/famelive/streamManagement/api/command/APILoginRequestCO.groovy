package com.famelive.streamManagement.api.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 27/10/14.
 */
class APILoginRequestCO extends APIRequestCO {

    {
        requestMethod = RequestType.POST
        contentType = "application/json; charset=utf8"
        //actionName = "login"
    }
    String email //= "admn.famelive@gmail.com"
    String password //= "admin"

    /*JSONObject toJson(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("appKey",appKey);
        jsonObject.put("apiVersion",apiVersion);
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        jsonObject.put("actionName",actionName);
        return jsonObject
    }*/
}
