https://blog.csdn.net/cmw1085215666/article/details/102142936

TKMybatis与Mybatis-plus都是mybatis的扩展，有相同的地方，也有不同的地方。
```text
1. 引入TkMybatis的Maven依赖
2. 实体类的相关配置,@Id,@Table
3. Mapper继承tkMabatis的Mapper接口
4. 启动类Application或自定义Mybatis配置类上使用@MapperScan注解扫描Mapper接口
5. 在application.properties配置文件中,配置mapper.xml文件指定的位置[可选]
6. 使用TkMybatis提供的sql执行方法

PS : 
    1. TkMybatis默认使用继承Mapper接口中传入的实体类对象去数据库寻找对应的表,因此如果表名与实体类名不满足对应规则时,会报错,这时使用@Table为实体类指定表。(这种对应规则为驼峰命名规则)
    2. 使用TkMybatis可以无xml文件实现数据库操作,只需要继承tkMybatis的Mapper接口即可。
    3. 如果有自定义特殊的需求,可以添加mapper.xml进行自定义sql书写,但路径必须与步骤4对应。
    
6. 如有需要,实现mapper.xml自定义sql语句
```

1、引入依赖
--
```xml
<!--springboot mybatis起步依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>${mybatis.spring.version}</version>
</dependency>

<!--tk.mybatis依赖-->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>${mybatis.spring.version}</version>
</dependency>

<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper</artifactId>
    <version>4.0.4</version>
</dependency>
```

2、引入逆向工程插件
--
```xml
<!--mybatis逆向工程插件-->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.7</version>
    <!--配置文件的位置-->
    <configuration>
        <!--允许移动生成的文件-->
        <verbose>true</verbose>
        <!--允许覆盖-->
        <overwrite>true</overwrite>
        <!--配置文件-->
        <configurationFile>src/main/resources/generator/generatorConfig.xml</configurationFile>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>4.0.4</version>
        </dependency>
    </dependencies>
</plugin>
```

3、配置文件
--
```properties
##################### mybatis ###############################
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.typeAliasesPackage=com.entity
mybatis.mapperLocations=classpath*:/com/mapper/**/*.xml
mybatis.configuration.use-generated-keys=true


pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql

##################### druid ###############################
```

4、逆向工程配置文件
--
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>

    <!--这里可以选择其他生成类型-->
    <context id="MySQLTables" targetRuntime="MyBatis3Simple">

        <!-- 生成的Java文件的编码 -->
        <property name="javaFileEncoding" value="UTF-8"/>
        <!-- 格式化java代码 -->
        <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>
        <!-- 格式化XML代码 -->
        <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>

        <!-- beginningDelimiter和endingDelimiter：指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号； -->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <!-- caseSensitive默认false，当数据库表名区分大小写时，可以将该属性设置为true -->
            <property name="caseSensitive" value="true"/>
        </plugin>
        
        <!--去除注释-->
        <commentGenerator>
            <property name="suppressAllComments" value="true" />
        </commentGenerator>

        <!--连接数据库 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/test?useSSL=false"
                        userId="root"
                        password="539976">
        </jdbcConnection>

        <!--java类型解析器-->
        <javaTypeResolver>
            <!--对数据库的查询结果进行trim操作-->
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!--生成实体类
        targetPackage：指定目标包名
        targetProject：指定目标工程
        -->
        <javaModelGenerator targetPackage="com.chance.entity" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!--sqlMap文件-->
        <sqlMapGenerator
                targetPackage="mapper"
                targetProject="src/main/resources">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!--生成dao接口-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.chance.mapper"
                             targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!--指定要生成数据库表-->
        <!--对应数据库表 mysql可以加入主键自增 字段命名 忽略某字段等 -->
        <table tableName="user" domainObjectName="User"
               enableCountByExample="false" enableUpdateByExample="false"
               enableDeleteByExample="false" enableSelectByExample="false"
               selectByExampleQueryId="false"/>

    </context>
</generatorConfiguration>
```

5、运行插件生成实体及相关文件dao
--
使用插件运行

6、什么是通用mapper
--
通用Mapper就是为了解决单表的增删改查，基于mybatis插件。
开发人员不需要编写SQL，不需要在DAO中增加方法，只要写好实体类，就能支持相应的增删改查方法。

7、实体类的写法
--
> 记住一个原则：实体类的字段数量>=数据库表中操作的字段数量。
默认情况下，实体列中的所有字段都会作为表中的字段来操作，如果有额外的字段，必须加上@Transient注解。

```text
1.表名默认使用类名，驼峰转下划线（只对大写字母进行处理）
2.表名可以使用@Table(name = “tableName”)指定，对不符合第一条默认规则的可以通过这种方式方式指定表名。
3.字段默认和@Column一样,都会作为表字段,表字段默认为Java对象的Field名字驼峰转下划线形式。
4.可以使用@Column(name = “fieldName”)指定不符合第3条规则的字段名。
5.使用@Transient注解可以忽略字段,添加该注解的字段不会作为表字段使用。
6.建议一定是有一个@Id注解作为主键的字段,可以有多个@Id注解的字段作为联合主键。
7.如果是MySQL的自增字段，加上@GeneratedValue(generator = “JDBC”)即可。如果是其他数据库，可以参考官网文档。
```

8、dao写法
--
在通用Mapper中，Dao只需要继承一个通用接口，即可拥有丰富的方法：
继承通用的Mapper必须指定泛型：
```java
public interface UserMapper extends Mapper<User> {
    
}
```

> 一旦继承了Mapper，继承的Mapper就拥有了Mapper所有的通用方法：
```text
Select

方法：List<T> select(T record);
说明：根据实体中的属性值进行查询，查询条件使用等号

方法：T selectByPrimaryKey(Object key);
说明：根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号

方法：List<T> selectAll();
说明：查询全部结果，select(null)方法能达到同样的效果

方法：T selectOne(T record);
说明：根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号

方法：int selectCount(T record);
说明：根据实体中的属性查询总数，查询条件使用等号

Insert

方法：int insert(T record);
说明：保存一个实体，null的属性也会保存，不会使用数据库默认值

方法：int insertSelective(T record);
说明：保存一个实体，null的属性不会保存，会使用数据库默认值

Update

方法：int updateByPrimaryKey(T record);
说明：根据主键更新实体全部字段，null值会被更新

方法：int updateByPrimaryKeySelective(T record);
说明：根据主键更新属性不为null的值

Delete

方法：int delete(T record);
说明：根据实体属性作为条件进行删除，查询条件使用等号

方法：int deleteByPrimaryKey(Object key);
说明：根据主键字段进行删除，方法参数必须包含完整的主键属性

Example方法

方法：List<T> selectByExample(Object example);
说明：根据Example条件进行查询
重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列

方法：int selectCountByExample(Object example);
说明：根据Example条件进行查询总数

方法：int updateByExample(@Param("record") T record, @Param("example") Object example);
说明：根据Example条件更新实体record包含的全部属性，null值会被更新

方法：int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);
说明：根据Example条件更新实体record包含的不是null的属性值

方法：int deleteByExample(Object example);
说明：根据Example条件删除数据
```

9、总结
--
通用Mapper的原理是通过反射获取实体类的信息，构造出相应的SQL，因此我们只需要维护好实体类即可。
实际项目中，要根据业务，在通用Mapper的基础上封装出粒度更大、更通用、更好用的方法。

