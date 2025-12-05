package com.chance.designpatterns.strategy;

import com.chance.designpatterns.anticorruptionlayer.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chance
 * @date 2025/12/5 09:24
 * @since 1.0
 */
@Service
public class PaymentService {

    // Spring 会自动找到所有 PaymentStrategy 的实现类（AliPayStrategy, WeChatPayStrategy等）
    // 并将它们注入到这个 Map 中。Key 是 Bean 的名称，Value 是策略实例。
    // 为了让 Key 变成我们业务需要的支付类型，我们可以在构造函数中做一次转换。
    private final Map<String, PaymentStrategy> strategyMap;

    /**
     * 构造器注入
     *
     * @param strategyList 策略列表
     */
    public PaymentService(List<PaymentStrategy> strategyList) {
        // 将 List 转换为 Map，以策略类型为 Key
        this.strategyMap = strategyList.stream().collect(
                Collectors.toMap(PaymentStrategy::getType, strategy -> strategy)
        );
    }

    /**
     * 消除 if-else 的核心分发方法
     *
     * @param type
     * @param request
     */
    public void dispatchPayment(String type, PaymentRequest request) {
        // 1. 通过 Key 快速查找策略
        PaymentStrategy strategy = strategyMap.get(type);

        // 2. 卫语句处理找不到策略的情况
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付类型：" + type);
        }

        // 3. 直接调用策略方法，代码中没有任何 if-else 或 switch！
        strategy.handlePayment(request);
    }
}
