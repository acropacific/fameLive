package com.famelive.streamManagement.api.command

import com.famelive.common.constant.CommonConstants
import com.famelive.streamManagement.command.RequestCO
import com.famelive.streamManagement.command.ResponseCO
import com.famelive.streamManagement.enums.APIRequestsDetails
import grails.converters.JSON
import grails.util.GrailsWebUtil
import org.codehaus.groovy.grails.web.json.JSONObject

import java.lang.reflect.Method

/**
 * Created by anil on 27/10/14.
 */
abstract class APIResponseCO extends ResponseCO {
    int status
    String detail
    String message

    APIResponseCO(InputStream inputStream, RequestCO requestCO) {
        if (requestCO._isTestRequest) {
            APIRequestsDetails requestActionDetails = APIRequestsDetails.find(requestCO.actionName)
            File responseFile = GrailsWebUtil.currentApplication().mainContext.getResource(CommonConstants.WOWZA_API_TEST_RESPONSE_PATH + "/" + requestActionDetails.testResponseFile).getFile()
            inputStream = (InputStream) new FileInputStream(responseFile)
        }
        JSONObject jsonObject = JSON.parse(inputStream, "UTF8")
        Class clazz = this.getClass()
        Method[] methods = clazz.getDeclaredMethods()
        Method[] parentMethods = clazz.superclass.getDeclaredMethods()
        methods = methods + parentMethods
        for (Method method : methods) {
            if (method.name.startsWith("set")) {
                String key = method.name.charAt(3).toLowerCase().toString();
                key += method.name.substring(4)
                try {
                    Object value = jsonObject.get(key)
                    if (value) {
                        method.invoke(this, value)
                    }
                } catch (Exception e) {
                    println "exception in method " + method.name + " exp:" + e
                }
            }
        }

    }
}
