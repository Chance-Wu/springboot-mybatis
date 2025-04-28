package com.chance.designpatterns.anticorruptionlayer;

/**
 * 防腐层实现
 * 该类的主要作用是作为领域模型与外部系统（如支付API）之间的适配器，实现两者的解耦
 * 通过本类，将领域模型转换为外部系统所需的格式，发送请求，并将外部系统的响应转换回领域模型
 *
 * @author chance
 * @date 2024/12/10 16:00
 * @since 1.0
 */
public class AntiCorruptionLayerServiceImpl implements AntiCorruptionLayerService {

    /**
     * 外部支付API接口
     */
    private final ExternalPaymentApi externalPaymentApi;

    /**
     * 构造函数，注入外部支付API
     *
     * @param externalPaymentApi 外部支付API实例
     */
    public AntiCorruptionLayerServiceImpl(ExternalPaymentApi externalPaymentApi) {
        this.externalPaymentApi = externalPaymentApi;
    }

    /**
     * 处理支付请求
     * 该方法首先将领域模型的支付请求转换为外部系统可接受的格式，然后调用外部支付API处理支付
     * 如果外部API调用失败，将捕获异常并返回失败结果
     *
     * @param paymentRequest 支付请求领域模型
     * @return 支付结果领域模型
     */
    @Override
    public PaymentResult processPayment(PaymentRequest paymentRequest) {
        // 转换领域模型到外部系统模型
        ExternalPaymentRequest externalRequest = mapToExternalRequest(paymentRequest);

        // 调用外部支付接口
        ExternalPaymentResponse response;
        try {
            response = externalPaymentApi.processPayment(externalRequest);
        } catch (Exception e) {
            // 处理外部系统调用异常
            return new PaymentResult(null, false, "Payment failed: " + e.getMessage());
        }

        // 转换外部系统响应到领域模型
        return mapToDomainResult(response);
    }

    /**
     * 数据转换：领域模型 -> 外部模型
     * 将支付请求的领域模型转换为外部支付系统所需的请求格式
     *
     * @param request 支付请求领域模型
     * @return 外部支付请求模型
     */
    private ExternalPaymentRequest mapToExternalRequest(PaymentRequest request) {
        ExternalPaymentRequest externalRequest = new ExternalPaymentRequest();
        externalRequest.setExternalOrderId(request.getOrderId());
        externalRequest.setTotalAmount(request.getAmount());
        externalRequest.setCurrencyCode(request.getCurrency());
        return externalRequest;
    }

    /**
     * 数据转换：外部模型 -> 领域模型
     * 将外部支付系统的响应转换为领域模型的支付结果
     *
     * @param response 外部支付响应模型
     * @return 支付结果领域模型
     */
    private PaymentResult mapToDomainResult(ExternalPaymentResponse response) {
        if (response.isStatus()) {
            return new PaymentResult(response.getTransactionId(), true, "Payment successful");
        } else {
            return new PaymentResult(null, false, response.getErrorMessage());
        }
    }
}
