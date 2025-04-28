package com.chance.designpatterns.bridge;

/**
 * 支付方式抽象接口
 * 定义支付过程中的抽象接口，并通过组合PaymentMode接口来实现支付方式的具体行为
 * 此设计模式允许支付方式和支付行为的独立变化，提高了系统的灵活性和可扩展性
 *
 * @author chance
 * @date 2024/12/26 14:55
 * @since 1.0
 */
public abstract class Payment {

    /**
     * 保存支付模式的引用，使得一个支付方式可以对应多种支付模式
     */
    protected PaymentMode paymentMode;

    /**
     * 构造方法，初始化支付模式
     *
     * @param paymentMode 支付模式接口，代表具体的支付方式
     */
    protected Payment(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * 定义支付行为的抽象方法，由子类实现具体支付逻辑
     * 不同的支付方式将实现不同的支付逻辑
     */
    public abstract void doPay();
}
