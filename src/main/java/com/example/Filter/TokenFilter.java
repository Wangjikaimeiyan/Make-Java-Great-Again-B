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
@WebFilter(value = "/*",asyncSupported = true)/* 拦截所有请求 */
public class TokenFilter implements Filter {
    //    拦截请求, 处理业务逻辑
    @Override
    public void doFilter(ServletRequest/* 请求*/ servletRequest, ServletResponse/* 响应*/ servletResponse, FilterChain/* 过滤器链*/ filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;/* 强转 */
//        获取请求路径
        String requestURI = request.getRequestURI();
//        ======================放行白名单======================
//        ======================放行白名单======================
//        ======================放行白名单======================
//        ======================放行白名单======================
//        判断是否是登录请求,如果是放行, 不是登录请求, 验证token
        if (requestURI.equals("/login")) {
            log.info("登录请求,放行");
            filterChain.doFilter(request, response);
            return;
        }
//        微信小程序登录接口 直接放行
        if (requestURI.equals("/WxUser/Login")) {
            log.info("微信登录请求，直接放行");
            filterChain.doFilter(request, response);
            return;
        }
//        // AI 流式聊天放行
//        String requestUri = request.getRequestURI();
//        if (requestUri.contains("/Chuan/ai/chat")) {
//            // 关键：手动设置 SSE 头，避免被过滤器/容器截断
//            response.setContentType("text/event-stream");
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setHeader("Connection", "keep-alive");
//
//            filterChain.doFilter(request, response);
//            return;
//        }
        // 匹配四个流式接口：统一校验Token
        String requestUri = request.getRequestURI();
// 定义要拦截并单独校验token的四个路径前缀
        boolean isChatApi =
                requestUri.contains("/Chuan/ai/chat") ||
                        requestUri.contains("/Xiang/ai/chat") ||
                        requestUri.contains("/Lu/ai/chat") ||
                        requestUri.contains("/Zhu/ai/chat");

        if (isChatApi) {
            // 1. 获取token
            String token = request.getHeader("token");
            if (token == null || token.isEmpty()) {
                log.info("流式聊天请求token为空，拦截");
                response.setStatus(401);
                return;
            }
            // 2. 校验token
            try {
                Claims claims = JwtUtils.parseJWT(token);
                Integer empId = (Integer) claims.get("id");
                CurrentHolder.setCurrentId(empId);
                log.info("流式聊天令牌校验成功，用户id:{}", empId);
            } catch (Exception e) {
                log.info("流式聊天令牌验证失败");
                response.setStatus(401);
                return;
            }
            // 3. 校验通过再设置SSE响应头
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Connection", "keep-alive");

            filterChain.doFilter(request, response);
            CurrentHolder.remove();
            return;
        }
//        ======================放行白名单======================
//        ======================放行白名单======================
//        ======================放行白名单======================
//        ======================放行白名单======================
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
            Integer empId = claims.get("id", Integer.class);
            String openid = claims.get("openid", String.class);

            if (empId != null) {
                // 后台账号：用员工id
                CurrentHolder.setCurrentId(empId);
                log.info("当前员工id为:{}", empId);
            } else if (openid != null) {
                // 微信小程序：用0
                CurrentHolder.setCurrentId(0);
                log.info("当前微信openid为:{}", openid);
            } else {
                // 既无id也无openid，直接算失败
                log.info("令牌无id和openid");
                response.setStatus(401);
                return;
            }
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

//    微信端口

}



































