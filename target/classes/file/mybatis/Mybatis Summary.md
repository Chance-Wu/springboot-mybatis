1、简介
--  
    1）支持自定义 SQL、存储过程以及高级映射。
    2）MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
    3）MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

> MyBatis通过xml或注解的方式将要执行的各种statement(preparedStatement等)配置起来。
> 并通过Java对象和Statement中的SQL的动态参数进行映射生成最终执行的SQL语句，最后由MyBatis框架执行SQL语句并将结果映射成Java对象并返回。 

> MyBatis在三层架构中处于DAO层，用于对数据库实现增删改查。

2、特点
--
> 在xml文件中配置SQL语句，实现了SQL语句与代码的分离，方便维护程序

> 可以结合数据库自身的特点灵活控制SQL语句，实现更高的查询效率，能够完成复杂查询。

3、体系结构
--

4、工作原理
--
    1）mybatis应用程序通过<u>SqlSessionFactoryBuilder</u>从mybatis-config.xml配置文件（也可用Java文件配置的方式，需要添加@Configuration）中构建出SqlSessionFactory(线程安全)；

    2）然后，SqlSessionFactory的实例直接开启一个SqlSession；

    3）再通过SqlSession实例获得Mapper对象并运行Mapper映射的SQL语句，完成对数据库的CRUD和事务提交，之后关闭SqlSession。

> 说明：SqlSession是单线程对象，因为它是非线程安全的，是持久化操作的独享对象，类似jdbc中的Connection，底层就封装了jdbc连接。

##### 详细流程如下：
    1）加载mybatis全局配置文件（数据源、mapper映射文件等），解析配置文件，Mybatis基于XML配置文件生成Configuration，和一个个MappedStatement（包括了参数映射配置、动态SQL语句、结果映射配置），其对应了<select | update | delete | insert>标签项；

    2）SqlSessionFactoryBuilder通过Configuration对象生成SqlSessionFactory，用来开启SqlSession；
    
    3）SqlSession对象完成和数据库的交互：
        a、用户程序调用mybatis接口层api（即Mapper接口中的方法）；
        
        b、SqlSession通过调用api的Statement ID找到对应的MappedStatement对象；
    
        c、通过Executor（负责动态SQL的生成和查询缓存的维护）将MappedStatement对象进行解析，sql参数转化、动态sql拼接，生成jdbc Statement对象；
    
        d、JDBC执行sql
        
        e、借助MappedStatement中的结果映射关系，将返回结果转化成HashMap、JavaBena等存储结构并返回。
