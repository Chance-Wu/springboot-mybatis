package com.chance.designpatterns.anticorruptionlayer;

/**
 * 应用服务使用防腐层
 *
 * @author chance
 * @date 2024/12/10 16:11
 * @since 1.0
 */
public class OrderService {

    private final AntiCorruptionLayerService antiCorruptionLayerService;

    public OrderService(AntiCorruptionLayerService antiCorruptionLayerService) {
        this.antiCorruptionLayerService = antiCorruptionLayerService;
    }

    public void processOrder(String orderId, double amount, String currency) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(amount);
        paymentRequest.setCurrency(currency);

        // 调用防腐层
        PaymentResult result = antiCorruptionLayerService.processPayment(paymentRequest);

        if (result.isSuccess()) {
            System.out.println("Payment processed successfully: " + result.getTransactionId());
        } else {
            System.out.println("Payment failed: " + result.getMessage());
        }
    }
}
