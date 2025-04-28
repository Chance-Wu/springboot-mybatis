package com.chance.designpatterns.adapter.object;

/**
 * 适配器类，用于将第三方支付接口转换为当前系统的支付网关接口
 * 此类实现了{@link PaymentGateway}接口，并内部封装了ThirdPartyPayment的逻辑
 * 适配器模式使得现有的第三方支付接口能够与当前系统兼容，无需修改第三方代码
 *
 * @author chance
 * @date 2024/12/6 16:45
 * @since 1.0
 */
public class PaymentAdapter implements PaymentGateway {

    /**
     * 第三方支付接口实例
     */
    private ThirdPartyPayment thirdPartyPayment;

    /**
     * 构造函数，初始化第三方支付接口实例
     *
     * @param thirdPartyPayment 第三方支付接口实例
     */
    public PaymentAdapter(ThirdPartyPayment thirdPartyPayment) {
        this.thirdPartyPayment = thirdPartyPayment;
    }

    /**
     * 实现PaymentGateway接口的pay方法
     * 调用第三方支付接口的process方法来处理支付请求
     *
     * @param amount 支付金额
     */
    @Override
    public void pay(double amount) {
        thirdPartyPayment.process(amount);
    }
}
