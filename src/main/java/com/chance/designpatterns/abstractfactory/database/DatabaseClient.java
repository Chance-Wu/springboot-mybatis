package com.chance.designpatterns.abstractfactory.database;

/**
 * 客户端
 * 该类负责使用数据库工厂创建的数据库产品族（连接、命令、结果集）
 *
 * @author chance
 * @date 2024/12/3 10:25
 * @since 1.0
 */
public class DatabaseClient {

    private final DatabaseFactory factory;

    private Connection connection;

    private Command command;

    private ResultSet resultSet;

    public DatabaseClient(DatabaseFactory factory) {
        this.factory = factory;
        this.connection = factory.createConnection();
        this.command = factory.createCommand();
        this.resultSet = factory.createResultSet();
    }

    public void connect() {
        connection.open();
    }

    public void disconnect() {
        connection.close();
    }

    public void executeQuery(String query) {
        command.execute(query);
        resultSet.process();
    }
}
