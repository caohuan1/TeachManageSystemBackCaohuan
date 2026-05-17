package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")//表示对所有请求路径开启拦截
public class AbcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest Request, ServletResponse Response, FilterChain Chain) throws IOException, ServletException {
        System.out.println("过滤器【AbcFilter】拦截到了请求，执行拦截前的逻辑...");
        Chain.doFilter(Request,Response);
        System.out.println("过滤器【AbcFilter】 执行拦截后的逻辑...");
    }
}
