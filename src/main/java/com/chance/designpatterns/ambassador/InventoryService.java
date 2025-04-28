package com.chance.designpatterns.ambassador;

/**
 * 库存服务接口
 *
 * @author chance
 * @date 2024/12/10 13:46
 * @since 1.0
 */
public interface InventoryService {

    /**
     * 获取库存
     * 根据产品ID查询当前的库存数量
     *
     * @param productId 产品ID，用于标识特定的产品
     * @return 当前产品的库存数量
     */
    int getStock(String productId);
}
