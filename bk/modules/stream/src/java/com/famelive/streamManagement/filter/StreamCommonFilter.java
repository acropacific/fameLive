package com.famelive.streamManagement.filter;

import com.famelive.common.request.MultiReadHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by anil on 30/10/14.
 */

//@WebFilter(filterName = "streamCommonFilter")
public class StreamCommonFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("streamCommonFilter initiated");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MultiReadHttpServletRequest request = new MultiReadHttpServletRequest((HttpServletRequest) servletRequest);
//        System.out.println("testing StreamCommonFilter");
        filterChain.doFilter(request, servletResponse);
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("streamCommonFilter destroyed");
    }
}
