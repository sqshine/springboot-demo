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
    private String[] excludedUrls;


    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        excludedUrls = filterConfig.getInitParameter("excludedUrls").split(",");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
        //String[] excludedUrls = {"/favicon.ico", ".js", ".gif", ".jpg", ".png", ".css", ".ico", "/country", "/druid"};
        // List<String> excludedUrls = Arrays.asList("/favicon.ico", ".js", ".gif", ".jpg", ".png", ".css", ".ico", "/country", "/druid");

        boolean match;
        for (String excludeUrl : excludedUrls) {
            match = path.contains(excludeUrl);
            if (match) {
                chain.doFilter(request, response);
                return;
            }
        }
        chain.doFilter(new XssHttpServletRequestWraper(req), response);
        logger.debug("==>XssFilter拦截请求");
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}