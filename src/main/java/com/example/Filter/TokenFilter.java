package com.example.Filter;

import com.example.Utils.CurrentHolder;
import com.example.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter(value = "/*",asyncSupported = true)/* 拦截所有请求 */
public class TokenFilter implements Filter {
    //    拦截请求, 处理业务逻辑
    @Override
    public void doFilter(ServletRequest/* 请求*/ servletRequest, ServletResponse/* 响应*/ servletResponse, FilterChain/* 过滤器链*/ filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;/* 强转 */
//        获取请求路径
        String requestURI = request.getRequestURI();
//        判断是否是登录请求,如果是放行, 不是登录请求, 验证token
        if (requestURI.equals("/login")) {
            log.info("登录请求,放行");
            filterChain.doFilter(request, response);
            return;
        }

//
        // AI 流式聊天放行
        String requestUri = request.getRequestURI();
        if (requestUri.contains("/Chuan/ai/chat")) {
            // 关键：手动设置 SSE 头，避免被过滤器/容器截断
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Connection", "keep-alive");

            filterChain.doFilter(request, response);
            return;
        }




//        获取 token
        String token = request.getHeader("token");
//        判断 token 是否为空,没有登陆返回401
        if (token == null || token.isEmpty()) {
            log.info("请求头token为空");
            response.setStatus(401);
            return;
        }
//        如果存在,校验令牌,失败返回401
        try {
            Claims claims = JwtUtils.parseJWT(token);
            Integer empId = (Integer) claims.get("id");
//            存入当前线程内存
            CurrentHolder.setCurrentId(empId);
            log.info("当前员工id为:{}", empId);
        } catch (Exception e) {
            log.info("令牌验证失败");
            response.setStatus(401);
            return;
        }
//        通过放行
        log.info("令牌验证成功");
        filterChain.doFilter(request, response);
//        删除当前线程内存
        CurrentHolder.remove();
    }
}
