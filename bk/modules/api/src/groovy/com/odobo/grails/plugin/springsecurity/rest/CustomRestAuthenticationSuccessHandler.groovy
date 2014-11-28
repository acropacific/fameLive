package com.odobo.grails.plugin.springsecurity.rest

import org.springframework.security.core.Authentication

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by anil on 18/9/14.
 */
class CustomRestAuthenticationSuccessHandler extends RestAuthenticationSuccessHandler {
    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.contentType = 'application/json'
        response.characterEncoding = 'UTF-8'
        response.addHeader 'Cache-Control', 'no-store'
        response.addHeader 'Pragma', 'no-cache'
        response << '{"status":1,"message":"success","detail":"","data":' + renderer.generateJson(authentication) + '}'
//        Map map=[status:1,message:"success",data:renderer.generateJson(authentication)];
//        Map m=JSON.parse(renderer.generateJson(authentication)) as Map
//        m.put("status",1)
//        m.put("message","success")
//        response << m as JSON
    }
}
