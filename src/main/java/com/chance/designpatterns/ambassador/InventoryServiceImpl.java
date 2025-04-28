package com.chance.designpatterns.ambassador;

/**
 * @author chance
 * @date 2024/12/10 14:31
 * @since 1.0
 */
public class InventoryServiceImpl implements InventoryService {

    @Override
    public int getStock(String productId) {
        System.out.println("Fetching stock for product: " + productId);
        // 模拟服务响应时间
        try {
            // 模拟网络延迟
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 模拟库存数据
        if ("P123".equals(productId)) {
            return 50; // 商品库存
        } else {
            throw new RuntimeException("Product not found!");
        }

    }
}
