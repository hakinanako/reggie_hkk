package org.com.hkk.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.com.hkk.reggie.common.BaseContext;
import org.com.hkk.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//登陆检查
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

//    public void init(FilterConfig config) throws ServletException {
//    }
//
//    public void destroy() {
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        log.info("拦截请求:{}", httpServletRequest.getRequestURI());

        String requestURI = httpServletRequest.getRequestURI();

        //不需要进行的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };


        boolean check = check(urls, requestURI);

        if (check) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            log.info("非拦截请求");
            return;
        }

        Object employee = httpServletRequest.getSession().getAttribute("employee");
        if (employee != null) {
            BaseContext.setCurrentId((Long) employee);
            chain.doFilter(httpServletRequest, httpServletResponse);
            log.info("已登陆用户");
            return;
        }

        //输出流

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    //调用匹配器进行路径匹配
    public boolean check(String[] uris, String requestURI) {
        for (String s : uris) {
            boolean match = PATH_MATCHER.match(s, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}


