package com.chance.designpatterns.ambassador;

/**
 * InventoryServiceAmbassador 类作为 InventoryService 的大使模式实现，旨在解耦客户端与库存服务的直接交互。
 * 它不仅处理库存查询逻辑，还负责输入验证、错误处理和重试逻辑，从而保护服务免受无效请求的影响，并增强系统的健壮性。
 *
 * @author chance
 * @date 2024/12/10 14:33
 * @since 1.0
 */
public class InventoryServiceAmbassador {

    private final InventoryService inventoryService;

    public InventoryServiceAmbassador(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * 根据产品ID获取库存数量。
     * 此方法首先验证产品ID的有效性，然后尝试调用 inventoryService 的 getStock 方法获取库存。
     * 如果因任何原因查询失败，将重试最多两次。如果所有尝试均失败，将返回 -1 表示错误。
     *
     * @param productId 产品的唯一标识符，用于查询库存。
     * @return 产品的库存数量，如果查询失败或产品ID无效，返回 -1。
     */
    public int getStock(String productId) {
        // 验证输入参数的有效性
        System.out.println("Ambassador: Validating input...");
        if (productId == null || productId.isEmpty()) {
            System.out.println("Ambassador: Invalid product ID.");
            // 表示错误
            return -1;
        }

        // 记录开始时间，用于计算整个过程的耗时
        long startTime = System.currentTimeMillis();
        // 初始化尝试次数
        int attempts = 0;
        // 定义最大重试次数
        int maxRetries = 2;

        // 尝试查询库存，最多重试两次
        while (attempts <= maxRetries) {
            attempts++;
            try {
                // 尝试调用库存服务
                System.out.println("Ambassador: Forwarding request to inventory service (attempt " + attempts + ")...");
                int stock = inventoryService.getStock(productId);
                System.out.println("Ambassador: Stock fetched successfully.");
                return stock;
            } catch (Exception e) {
                // 处理查询过程中出现的异常
                System.out.println("Ambassador: Error occurred - " + e.getMessage());
                if (attempts > maxRetries) {
                    System.out.println("Ambassador: Max retries reached. Returning error.");
                    // 表示错误
                    return -1;
                }
                System.out.println("Ambassador: Retrying...");
            }
        }

        // 计算并打印整个过程的耗时
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Ambassador: Process took " + elapsedTime + " ms.");
        // 如果未成功，返回错误码
        return -1;
    }
}
