### SpringBoot集成Mybatis，数据库连接池用druid

#### 1、maven配置
    <!--springboot mybatis起步依赖-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${mybatis.springboot.version}</version>
    </dependency>
    
    <!--mybatis逆向工程-->
    <dependency>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-core</artifactId>
        <version>1.3.7</version>
    </dependency>
    
    <!--druid连接池-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>${druid.version}</version>
    </dependency>
    
    <!--mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!--设定Maven主仓库为阿里私服-->
    <repositories>
        <repository>
            <id>repo</id>
            <name>Repository</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </repository>
    </repositories>
    <!--设定插件仓库-->
    <pluginRepositories>
        <pluginRepository>
            <id>pluginsRepo</id>
            <name>PluginsRepository</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </pluginRepository>
    </pluginRepositories>
    
    <!--mybatis逆向工程插件-->
    <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.7</version>
        <configuration>
            <overwrite>true</overwrite>
            <configurationFile>src/main/resources/generator/generatorConfig.xml</configurationFile>
        </configuration>
    </plugin>

#### 2、SpringBoot配置文件
配置数据源和druid连接池


#### 3、启动类注解
    @EnableAsync //开启对异步方法的支持
    @EnableTransactionManagement //开启事务管理配置的支持
    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
            MybatisAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class})

#### 4、Mybatis和数据源配置类

##### 4.1 配置数据源
使用Druid连接池

##### 4.2 配置Mybatis
    1.创建主数据源的事务管理
    2.创建Mybatis的连接会话工厂实例








#### 注解总结
    @EnableAsync //开启对异步方法的支持
    @EnableTransactionManagement //开启事务管理配置的支持
    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
            MybatisAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class})

    












