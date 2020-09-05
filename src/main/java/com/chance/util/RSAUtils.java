package com.chance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-05
 */
public class RSAUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    /**
     * 使用模和指数生成RSA私钥 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同的JDK默认的补位方式可能不同】
     *
     * @param modules 模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modules, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modules);
            BigInteger b2 = new BigInteger(modules);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
