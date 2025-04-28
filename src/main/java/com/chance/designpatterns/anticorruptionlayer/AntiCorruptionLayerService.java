package com.chance.designpatterns.anticorruptionlayer;

/**
 * 防腐层接口
 * 防腐层的设计目的是在系统的不同部分之间提供一个解耦层，
 * 以减少系统组件之间的直接依赖，提高系统的可维护性和扩展性。本接口主要用于处理支付请求，
 * 通过封装支付处理逻辑，对外提供统一的支付处理接口，同时隐藏内部实现细节，增加系统的灵活性和容错能力。
 *
 * @author chance
 * @date 2024/12/10 15:58
 * @since 1.0
 */
public interface AntiCorruptionLayerService {

    /**
     * 处理支付请求
     * 本方法接收一个支付请求对象，对支付请求进行处理，并返回支付结果。通过本方法的实现，
     * 可以将支付处理逻辑与系统的其他部分隔离开来，减少支付系统与其他系统组件之间的直接依赖，
     * 提高系统的可维护性和扩展性。
     *
     * @param paymentRequest 支付请求对象，包含支付所需的信息
     * @return PaymentResult 支付结果对象，表示支付的处理结果
     */
    PaymentResult processPayment(PaymentRequest paymentRequest);
}
