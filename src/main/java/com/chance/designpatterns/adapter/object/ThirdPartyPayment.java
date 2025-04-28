package com.chance.designpatterns.adapter.object;

/**
 * 第三方支付类
 * 该类提供了处理支付的功能，主要用于模拟第三方支付流程
 * 它允许通过特定的支付方法来执行支付操作，当前支持的支付方法定义在process方法中
 *
 * @author chance
 * @date 2024/12/6 16:44
 * @since 1.0
 */
public class ThirdPartyPayment {

    public void process(double value) {
        System.out.println("Third-party payment processed: " + value);
    }
}
