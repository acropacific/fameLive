package com.famelive.streamManagement

import com.famelive.streamManagement.command.RequestCO
import com.famelive.streamManagement.command.ResponseCO
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.json.JSONObject

import javax.servlet.http.HttpServletRequest
import java.lang.reflect.Method

@Transactional
class RequestHandlerService {
    GrailsApplication grailsApplication

    boolean isAuthorisedRequest(String token) {
    }

    ResponseCO process(HttpServletRequest request, RequestCO requestCO, String apiVersion) {
        APIService apiService = grailsApplication.mainContext.getBean("streamAPI" + apiVersion.toUpperCase() + "Service")
        Class clazz = apiService.getClass()
        Method method = clazz.getMethod(requestCO.actionName, JSONObject.class)
        JSONObject jsonObject = request.JSON
        jsonObject.put("clientIP", request.getRemoteAddr())
        return method.invoke(apiService, jsonObject)
    }
}
