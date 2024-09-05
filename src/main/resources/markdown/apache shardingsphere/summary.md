Apache ShardingSphere 是一套开源的分布式数据库中间件解决方案组成的生态圈，它由 JDBC、Proxy 和 Sidecar(规划中)这 3 款相互独立，却又能够混合部署配合使用的产品组成。
* 它们均提供标准化的数 据分片、分布式事务和数据库治理功能，可适用于如 Java 同构、异构语言、云原生等各种多样化的应用 场景。
* 定位为关系型数据库中间件，旨在充分合理地在分布式的场景下利用关系型数 据库的计算和存储能力，而并非实现一个全新的关系型数据库。它通过关注不变，进而抓住事物本质。关 系型数据库当今依然占有巨大市场，是各个公司核心业务的基石。
* 5.x 版本开始致力于可插拔架构，项目的功能组件能够灵活的以可插拔的方式 进行扩展。目前，数据分片、读写分离、多数据副本、数据加密、影子库压测等功能，以及对 MySQL、 PostgreSQL、SQLServer、Oracle 等 SQL 与协议的支持，均通过插件的方式织入项目。开发者能够像使用 积木一样定制属于自己的独特系统。

> 核心功能

* 数据分片
* 分布式事务
* 数据库治理
* 多模式连接
* 管控界面

> 接入端 （微内核  云原生 零侵入）

* Sharding-JDBC
* Sharding-Proxy
* Sharding-Sidecar

> 开放生态


1、ShardingSphere-JDBC
--
定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。它使用客戶端直连数据库，以 jar 包形式 提供服务，无需额外部署和依赖，可理解为增强版的 JDBC 驱动，完全兼容 JDBC 和各种 ORM 框架。
* 适用于任何基于 JDBC 的 ORM 框架，如:JPA, Hibernate, Mybatis, Spring JDBC Template 或直接 使用 JDBC。
* 支持任何第三方的数据库连接池，如:DBCP, C3P0, BoneCP, Druid, HikariCP 等。
* 支持任意实现JDBC规范的数据库，目前支持MySQL，Oracle，SQLServer，PostgreSQL以及任何
遵循 SQL92 标准的数据库。

2、ShardingSphere-Proxy
--
定位为透明化的数据库代理端，提供封装了数据库二进制协议的服务端版本，用于完成对异构语言的支持。目前提供 MySQL 和 PostgreSQL 版本，它可以使用任何兼容 MySQL/PostgreSQL 协议的访问客戶端 (如:MySQL Command Client, MySQL Workbench, Navicat 等) 操作数据，对 DBA 更加友好。
* 向应用程序完全透明，可直接当做 MySQL/PostgreSQL 使用。 
* 适用于任何兼容 MySQL/PostgreSQL 协议的的客戶端。

3、ShardingSphere-Sidecar
--
定位为 Kubernetes 的云原生数据库代理，以 Sidecar 的形式代理所有对数据库的访问。

|   |ShardingSphere-JDBC|ShardingSphere-Proxy|ShardingSphere-Sidecar|
|---|---|---|---|
|数据库|任意|MySQL/PostgreSQL|MySQL/PostgreSQL
|连接消耗数|高|低|高|
|异构语言|仅Java|任意|任意|
|性能|损耗低|损耗略高|损耗低|
|无中心化|是|否|是|
|静态入口|无|有|无|

4、混合架构
--
* ShardingSphere-JDBC 采用无中心化架构，适用于 Java 开发的高性能的轻量级 OLTP 应用; 
* ShardingSphere-Proxy 提供静态入口以及异构语言的支持，适用于 OLAP 应用以及对分片数据库进行管理和运维的场景。
* Apache ShardingSphere 是多接入端共同组成的生态圈。通过混合使用 ShardingSphere-JDBC 和 ShardingSphere-Proxy，并采用同一注册中心统一配置分片策略，能够灵活的搭建适用于各种场景的应用系统，使得架构师更加自由地调整适合与当前业务的最佳系统架构。

