package file.ambitlu.work.config;


import file.ambitlu.work.entity.StatusCode;
import file.ambitlu.work.util.TokenRsa;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/9 18:33
 * @description:
 */
@WebFilter(filterName = "FileFilter", urlPatterns = "/file/*")
@Slf4j
public class FileFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("全局过滤器开始工作");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;


        /*  允许跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "*");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-type", "application/json");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");

        System.out.println(request.getHeader("Authorization"));

        boolean authentication = TokenRsa.Authentication(request, response);
        if (authentication){
            filterChain.doFilter(request,response);
        }else {
//            servletRequest.setAttribute("data", data);
            servletRequest.getRequestDispatcher("/error/"+ StatusCode.ACCESSERROR.getCode()).forward(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
