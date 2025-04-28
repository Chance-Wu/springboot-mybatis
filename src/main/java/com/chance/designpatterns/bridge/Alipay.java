package com.chance.designpatterns.bridge;

/**
 * 具体支付方式
 *
 * @author chance
 * @date 2024/12/26 15:23
 * @since 1.0
 */
public class Alipay extends Payment {

    public Alipay(PaymentMode paymentMode) {
        super(paymentMode);
    }

    @Override
    public void doPay() {
        System.out.print("支付宝");
        paymentMode.pay();
    }
}
