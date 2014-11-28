package com.famelive.streamManagement.api.command

import com.famelive.streamManagement.command.RequestCO
import org.codehaus.groovy.grails.web.json.JSONObject

import java.lang.reflect.Method

/**
 * Created by anil on 27/10/14.
 */
abstract class APIRequestCO extends RequestCO {
    {
        appKey = "myAppKey"
        apiVersion = "1.0"
    }

    JSONObject toJson() {
        Class clazz = this.getClass()

        Method[] methods = clazz.getDeclaredMethods()
        Method[] parentMethods = clazz.superclass.getDeclaredMethods()
        methods = methods + parentMethods
        JSONObject jsonObject = new JSONObject()
        for (Method method : methods) {
            if (method.name.startsWith("get")) {
                String key = method.name.charAt(3).toLowerCase().toString();
                key += method.name.substring(4)
                try {
                    jsonObject.put(key, (String) method.invoke(this))
                } catch (Exception e) {
                    println "exception in " + method.name + " exp:" + e
                }
            }
        }
        return jsonObject
    }
}
