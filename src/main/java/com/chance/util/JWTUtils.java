package com.chance.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-05
 */
public class JWTUtils {

    public JWTUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    private static final RSAPrivateKey PRIVATE_KEY;

    static {
        String modules = "";
        String privateExponent = "";
        // 以上不能保证数据被恶意篡改，原始数据和哈希值都可能被恶意篡改，要保证不被篡改，可以使用RSA 公钥私钥方案，再配合哈希值。
        PRIVATE_KEY = RSAUtils.getPrivateKey(modules,privateExponent);
    }

    /***
     * 获取token
     * @param uid 用户id
     * @param exp 失效时间，单位分钟
     * @return
     */
    public static String getToken(String uid, long exp) {
        long endTime = System.currentTimeMillis() + exp;
        return Jwts.builder()
                .setSubject(uid)
                .setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, PRIVATE_KEY)
                .compact();
    }

}
