package com.personal.blog.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: luxinlong
 * @Description:
 */
@ControllerAdvice
public class MyException {

    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView myExceptionHandler(HttpServletRequest request,Exception exception)throws Exception{
        //打印URL和异常
        logger.info("Request Url:{}\nException:{}",request.getRequestURL(),exception);

        //如果状态码不为null,抛出异常,交给springboot处理,否则,跳转到自定义的error界面
        if(AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class)!=null){
            throw exception;
        }

        //跳转到自定义的error界面
        ModelAndView modelAndView = new ModelAndView();

        StringBuilder builder = new StringBuilder(120);
        builder.append("错误URL：");
        builder.append(request.getRequestURL());
        builder.append("\t");
        builder.append("异常信息: ");
        builder.append(exception.toString());

        modelAndView.addObject("message",builder.toString());
        modelAndView.setViewName("/error/error");
        return modelAndView;
    }
}

