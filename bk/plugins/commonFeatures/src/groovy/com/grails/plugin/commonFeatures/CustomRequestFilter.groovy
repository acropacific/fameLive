package com.grails.plugin.commonFeatures

import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomRequestFilter extends GenericFilterBean {

    @Override
    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req
        HttpServletResponse response = (HttpServletResponse) res

        CustomRequestWrapper requestWrapper = new CustomRequestWrapper(request)

        chain.doFilter(requestWrapper, response)
    }
}
