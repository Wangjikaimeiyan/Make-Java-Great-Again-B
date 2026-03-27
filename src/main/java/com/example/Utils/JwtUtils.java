package com.example.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {

    private static final String signKey = "X+3k9ZpQ7sL8aG2bN1cV5dF8gH0jK3lM6nO9pR2tU5vW7yZ1bC3e";
    private static final Long expire = 12 * 60 * 60 * 1000L;/* 令牌过期时间 12小时 */

    /**
     * 生成JWT令牌
     */
    public static String generateJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)/* 添加令牌 */
                .signWith(SignatureAlgorithm.HS256, signKey)/* 使用HS256算法进行签名，并设置签名秘钥 */
                .setExpiration(new Date(System.currentTimeMillis()/* 当前时间*/ + expire))/* 设置令牌过期时间 */
                .compact();/* 生成JWT令牌 */
        Date expDate = new Date(System.currentTimeMillis() + expire);
        log.info("令牌生成时间：{}，过期时间：{}", new Date(), expDate);
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        return Jwts.parser()/* 创建JWT解析器 */
                .setSigningKey(signKey)/* 设置签名秘钥 */
                .parseClaimsJws(jwt)/* 解析JWT令牌 */
                .getBody();
    }
}
