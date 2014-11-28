package com.famelive.api.security

import com.famelive.common.request.MultiReadHttpServletRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.web.filter.GenericFilterBean

import javax.naming.AuthenticationNotSupportedException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by anil on 16/9/14.
 */
class APIValidationFilter extends GenericFilterBean implements ApplicationEventPublisherAware {
    def grailsApplication
    def authenticationManager
    def eventPublisher
    def rememberMeServices
    def springSecurityService
    AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler()
    AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler()

    void afterPropertiesSet() {
        assert authenticationManager != null, 'authenticationManager must be specified'
        assert rememberMeServices != null, 'rememberMeServices must be specified'
    }

    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        MultiReadHttpServletRequest request = new MultiReadHttpServletRequest((HttpServletRequest) req);
//        HttpServletRequest request = (HttpServletRequest) req
        HttpServletResponse response = (HttpServletResponse) res
        /*def apiVersion = request.getParameter("apiVersion")
        def appKey = request.getParameter("appKey")
        InputStream inputStream = request.getInputStream()
        byte[] bytes = inputStream.getBytes()*/

        def jsonObject = request.JSON
        def apiVersion = jsonObject.getAt(grailsApplication.config.famelive.apiVersionPropertyName)
        def appKey = jsonObject.getAt(grailsApplication.config.famelive.appKeyPropertyName)
        if (jsonObject.size() > 0)
            if (!(((List) (grailsApplication.config.famelive.supported.api.versions)).contains(apiVersion) && grailsApplication.config.famelive.supported.api.appKey.equals(appKey))) {
                throw new AuthenticationNotSupportedException('Invalid API version, please upgrade app version')
            }
        chain.doFilter(request, res)
//        chain.doFilter(req, res)
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        SecurityContextHolder.getContext().setAuthentication(authResult)
        rememberMeServices.onLoginSuccess(request, response, authResult)
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();
        rememberMeServices.loginFail(request, response)
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher
    }
}
