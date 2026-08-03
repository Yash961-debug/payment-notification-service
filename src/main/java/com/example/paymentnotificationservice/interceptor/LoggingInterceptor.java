package com.example.paymentnotificationservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        System.out.println("======================================");
        System.out.println("Incoming Request");
        System.out.println("Method : " + request.getMethod());
        System.out.println("URI    : " + request.getRequestURI());
        System.out.println("Time   : " + LocalDateTime.now());
        System.out.println("======================================");

        return true;
    }
}