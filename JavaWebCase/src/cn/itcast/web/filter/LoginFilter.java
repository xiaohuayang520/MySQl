package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转
        HttpServletRequest request = (HttpServletRequest) req;

        String uri = request.getRequestURI();
        //判断是否包含这些路径
        if (uri.contains("/login.jsp") || uri.contains("/LoginServlet") || uri.contains("/css/") || uri.contains("/fonts/") || uri.contains("/js/") || uri.contains("/checkCode")) {
            //包含，则放行
            chain.doFilter(req, resp);
        } else {
            //判断是否登录
            Object student = request.getSession().getAttribute("student");
            if (student == null) {
                //每有数据则没有登录
                request.getSession().setAttribute("login","请登录！！！");
                request.getRequestDispatcher("/login.jsp").forward(req,resp);
            } else {
                //有数据则登录过，放行
                chain.doFilter(req, resp);
            }
        }
        //放行
        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
