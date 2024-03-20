package com.chance.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

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
public class JwtUtils {

    private JwtUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final RSAPrivateKey PRIVATE_KEY;

    static {
        String modules = "";
        String privateExponent = "";
        // 以上不能保证数据被恶意篡改，原始数据和哈希值都可能被恶意篡改，要保证不被篡改，可以使用RSA 公钥私钥方案，再配合哈希值。
        PRIVATE_KEY = RSAUtils.getPrivateKey(modules);
    }

    /***
     * 获取token
     * @param uid 用户id
     * @param exp 失效时间，单位分钟
     * @return
     */
    public static String getToken(String uid, long exp) {
        long endTime = System.currentTimeMillis() + 1000 * 60 * exp;
        return Jwts.builder()
                .setSubject(uid)
                .setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, PRIVATE_KEY)
                .compact();
    }

    public static JwtResult checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(PRIVATE_KEY).parseClaimsJws(token).getBody();
            String sub = claims.get("sub", String.class);
            logger.debug("token.sub:{}", sub);
            return new JwtResult(true, sub, "合法请求", HttpStatus.OK.value());
        } catch (ExpiredJwtException e) {
            // 在解析JWT字符串时，如果'过期时间字段'早于当前时间，将会抛出异常，说明本次请求已经失效
            return new JwtResult(false, null, "token已过期", HttpStatus.PAYMENT_REQUIRED.value());
        } catch (SignatureException e) {
            // 在解析JWT字符串时，如果秘钥不正确，将会解析失败，抛出异常，说明该JWT字符串是伪造的
            return new JwtResult(false, null, "非法请求", HttpStatus.FORBIDDEN.value());
        } catch (Exception e) {
            return new JwtResult(false, null, "非法请求" + e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    public static class JwtResult {

        private boolean status;
        private String uid;
        private String msg;
        private int code;

        public JwtResult() {
            super();
        }

        public JwtResult(boolean status, String uid, String msg, int code) {
            this.status = status;
            this.uid = uid;
            this.msg = msg;
            this.code = code;
        }

        public boolean getStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "JwtResult{" +
                    "status=" + status +
                    ", uid='" + uid + '\'' +
                    ", msg='" + msg + '\'' +
                    ", code=" + code +
                    '}';
        }
    }
}
