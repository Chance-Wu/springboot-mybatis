只是Mybatis的增强工具，在Mybatis的基础上只做增强不做改变。

特性
--
* 无侵入

* 损耗小：启动即自动注入基本CRUD，性能基本无损耗，直接面向对象操作。

* 强大的CRUD操作：内置通用Mapper、通用Service，仅仅通过少量配置即可实现单表大部分CRUD操作，更有强大的条件构造器，满足各种使用需求。

* 支持Lambda形式调用：方便编写各类查询条件，无需担心字段写错。

* 支持主键自动生成：支持4种主键策略（内含分布式唯一ID生成器-Sequence），可自由配置。

* 支持ActiveRecord模式：实体类只需继承 Model 类即可进行强大的 CRUD 操作。

* 支持自定义全局通用操作：支持全局通用方法注入。

* 内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎

* 内置分页插件：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询

* 分页插件支持多种数据库

* 内置性能分析插件：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能。

* 内置全局拦截插件

