package com.chance.designpartterms;

import com.chance.designpatterns.adapter.clazz.FileProcessorAdapter;
import com.chance.designpatterns.adapter.object.PaymentAdapter;
import com.chance.designpatterns.adapter.object.PaymentGateway;
import com.chance.designpatterns.adapter.object.ThirdPartyPayment;
import org.junit.Test;

/**
 * 适配器模式测试
 *
 * @author chance
 * @date 2024/12/5 16:43
 * @since 1.0
 */
public class AdapterTest {

    @Test
    public void testObjectAdapter() {
        // 创建ThirdPartyPayment实例
        ThirdPartyPayment thirdPartyPayment = new ThirdPartyPayment();
        PaymentGateway gateway = new PaymentAdapter(thirdPartyPayment);
        gateway.pay(200.0);
    }

    /**
     * 测试类适配器
     * 使用FileProcessorAdapter实例来处理文件
     * 此方法展示了如何使用适配器模式来调用文件处理功能
     */
    @Test
    public void testClassAdapter() {
        // 创建FileProcessorAdapter实例
        FileProcessorAdapter fileProcessorAdapter = new FileProcessorAdapter();
        // 调用处理文件的方法，传入文件名为参数
        fileProcessorAdapter.processFile("example.txt");
    }
}
