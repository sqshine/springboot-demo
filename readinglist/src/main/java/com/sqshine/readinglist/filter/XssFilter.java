package com.sqshine.readinglist.filter;

import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author sqshine
 */
public class XssFilter implements Filter {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(XssFilter.class);

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWraper((HttpServletRequest) request), response);
        logger.debug("==>XssFilter拦截请求");
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}