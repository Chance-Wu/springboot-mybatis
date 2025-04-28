package com.chance.designpartterms;

import com.chance.designpatterns.abstractfactory.database.DatabaseClient;
import com.chance.designpatterns.abstractfactory.database.DatabaseFactory;
import com.chance.designpatterns.abstractfactory.database.MySQLFactory;
import com.chance.designpatterns.abstractfactory.database.PostgreSQLFactory;
import com.chance.designpatterns.abstractfactory.kingdom.Army;
import com.chance.designpatterns.abstractfactory.kingdom.Castle;
import com.chance.designpatterns.abstractfactory.kingdom.FactoryMaker;
import com.chance.designpatterns.abstractfactory.kingdom.King;
import com.chance.designpatterns.abstractfactory.kingdom.KingdomFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/2 16:51
 * @since 1.0
 */
@Slf4j
public class AbstractFactoryTest {

    @Test
    public void datebase() {
        // 根据配置选择合适的数据库工厂
        String dbType = "mysql"; // 假设这是从配置文件或用户输入获取的

        DatabaseFactory factory;
        if ("mysql".equalsIgnoreCase(dbType)) {
            factory = new MySQLFactory();
        } else if ("postgresql".equalsIgnoreCase(dbType)) {
            factory = new PostgreSQLFactory();
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }

        DatabaseClient client = new DatabaseClient(factory);
        client.connect();
        client.executeQuery("SELECT * FROM users");
        client.disconnect();
    }

    @Test
    public void kingdom() {
        log.info("elf kingdom");
        KingdomFactory factory = FactoryMaker.makeFactory(FactoryMaker.KingdomType.ELF);
        Castle castle = factory.createCastle();
        King king = factory.createKing();
        Army army = factory.createArmy();
        log.info(castle.getDescription());
        log.info(king.getDescription());
        log.info(army.getDescription());

        log.info("orc kingdom");
        KingdomFactory factory2 = FactoryMaker.makeFactory(FactoryMaker.KingdomType.ORC);
        Castle castle2 = factory2.createCastle();
        King king2 = factory2.createKing();
        Army army2 = factory2.createArmy();
        log.info(castle2.getDescription());
        log.info(king2.getDescription());
        log.info(army2.getDescription());
    }
}
