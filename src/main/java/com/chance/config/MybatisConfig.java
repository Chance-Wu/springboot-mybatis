//package com.chance.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
///**
// * <p>
// * Mybatis配置类
// * <p>
// *
// * @author chance
// * @since 2020-08-15
// */
//@MapperScan(basePackages = {"com.chance.mapper"}, sqlSessionFactoryRef = "primarySessionFactory")
//@ComponentScan
//@EnableTransactionManagement
//@Configuration
//public class MybatisConfig {
//
//    /**创建主数据源的事务管理*/
//    @Bean(name = "primaryTransactionManager")
//    public DataSourceTransactionManager transactionManager(
//            @Qualifier("primaryDataSource") DataSource primaryDataSource) {
//        return new DataSourceTransactionManager(primaryDataSource);
//    }
//
//    /**创建Mybatis的连接会话工厂实例*/
//    @Bean(name = "primarySessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(
//            @Qualifier("primaryDataSource") DataSource primaryDataSource) {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(primaryDataSource);
//        factoryBean.setTypeAliasesPackage("com.chance.entity");
//
//        //分页插件
//
//        //添加XML目录
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
//            return factoryBean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }
//}
