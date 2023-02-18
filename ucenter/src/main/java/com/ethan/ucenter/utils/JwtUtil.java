package com.ethan.ucenter.utils;

import com.ethan.ucenter.config.BaseConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @author Ethan 2023/2/15
 */
public class JwtUtil {

    // 过期时间默认一周，单位是毫秒
    private static long ttl = BaseConfig.TIME_WEEK_IN_MILLI;

    /**
     * @param claims 载荷内容
     * @param ttl    有效时长
     * @return Token
     */
    public static String createToken(Map<String, Object> claims, long ttl, String key) {
        JwtUtil.ttl = ttl;
        return createToken(claims, key);
    }

    /**
     * @param claims 载荷
     * @return token
     */
    public static String createToken(Map<String, Object> claims, String key) {
        // Header
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key); // 用户的盐值就是 key，算法+key 共同签名形成 signature 部分

        // Payload
        if (claims != null) {
            builder.setClaims(claims);
        }

        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        // 算法+key 共同签名形成 Signature 部分
        return builder.compact();
    }

    public static String createRefreshToken(String userId, long ttl, String key) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析 Token 的 Payload 内容
     * 先 Base64 解码 Header 部分拿到所用算法，再用传入的 key+算法 重新加密得到Signature，比较 Signature 可判断 Token 真伪
     * 如为真，返回 Payload 内容
     */
    public static Claims parseJWT(String jwtStr, String key) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
