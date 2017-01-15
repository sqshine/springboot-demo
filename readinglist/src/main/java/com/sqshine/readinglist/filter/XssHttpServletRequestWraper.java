package com.sqshine.readinglist.filter;


import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 防止xss攻击
 **/
public class XssHttpServletRequestWraper extends HttpServletRequestWrapper {

    XssHttpServletRequestWraper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        return clearXss(super.getParameter(name));
    }

    @Override
    public String getHeader(String name) {
        return clearXss(super.getHeader(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            String[] newValues = new String[values.length];

            for (int i = 0; i < values.length; i++) {
                newValues[i] = clearXss(values[i]);
            }
            return newValues;
        }
        return super.getParameterValues(name);
    }

    /**
     * 处理字符转义
     *
     * @param value 待处理字符串
     * @return 处理后的字符串
     */
    private String clearXss(String value) {
        return HtmlUtils.htmlEscape(value);
    }

}