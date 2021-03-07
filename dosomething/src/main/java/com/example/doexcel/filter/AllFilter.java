package com.example.doexcel.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author huanliu
 */
@WebFilter(filterName = "allFilter", urlPatterns = {"/pr"})
public class AllFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig){

    }

    /**
     * 过滤逻辑核心类
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //请求header中的'referer'参数值表示请求发起者发起者的页面地址，在安全方面可以用来筛选指定域名或者IP不允许通过
        String referer = request.getHeader("referer");
        if (referer == null || "".equals(referer)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.getWriter().print("访问来源错误");
        }
        System.out.println("进入过滤器");
    }

    @Override
    public void destroy() {

    }
}
