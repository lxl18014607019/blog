package com.personal.blog.interceptor;

import com.personal.blog.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: luxinlong
 * @Description:
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
//      判断请求是否包含admin
        if(uri.contains("admin")){
            boolean result=false;
            User user = (User)request.getSession().getAttribute("user");
            if(user!=null){
                result=true;
            }else{
                /*此处不能写/admin/ 返回上级*/
                response.sendRedirect("/admin");
            }
            return result;
        }else if(uri.contains("projects")){
            String code = (String)request.getSession().getAttribute("code");
            if(code!=null){
                return true;
            }else {
                response.sendRedirect("/messageboard");
                return false;
            }
        }else {
            return true;
        }
    }
}

