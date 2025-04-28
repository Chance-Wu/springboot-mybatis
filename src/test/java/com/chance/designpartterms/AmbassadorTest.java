package com.chance.designpartterms;

import com.chance.designpatterns.ambassador.InventoryService;
import com.chance.designpatterns.ambassador.InventoryServiceAmbassador;
import com.chance.designpatterns.ambassador.InventoryServiceImpl;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/10 14:36
 * @since 1.0
 */
public class AmbassadorTest {

    @Test
    public void test() {
        // 创建远程服务实例
        InventoryService remoteService = new InventoryServiceImpl();

        // 创建大使类实例
        InventoryServiceAmbassador ambassador = new InventoryServiceAmbassador(remoteService);

        // 模拟业务调用
        System.out.println("Client: Checking stock for product P123...");
        int stock = ambassador.getStock("P123");
        if (stock >= 0) {
            System.out.println("Client: Product stock: " + stock);
        } else {
            System.out.println("Client: Failed to fetch product stock.");
        }

        System.out.println("\nClient: Checking stock for an invalid product...");
        stock = ambassador.getStock("INVALID");
        if (stock >= 0) {
            System.out.println("Client: Product stock: " + stock);
        } else {
            System.out.println("Client: Failed to fetch product stock.");
        }
    }
}
