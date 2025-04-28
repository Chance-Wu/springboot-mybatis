package com.chance.designpatterns.bridge;

/**
 * @author chance
 * @date 2024/12/26 14:54
 * @since 1.0
 */
public class FacePayMode implements PaymentMode {
    @Override
    public void pay() {
        System.out.println("人脸支付");
    }
}
