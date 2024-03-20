package com.chance.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author: chance
 * @date: 2024/3/20 13:58
 * @since: 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.chance.mapper.clickhouse"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class ClickhouseDataSourceConfig {

    @Resource
    private ClickhouseJdbcConfig clickhouseJdbcConfig;

    @Bean("dataSource2")
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(clickhouseJdbcConfig.getUrl());
        datasource.setDriverClassName(clickhouseJdbcConfig.getDriverClassName());
        datasource.setInitialSize(clickhouseJdbcConfig.getInitialSize());
        datasource.setMinIdle(clickhouseJdbcConfig.getMinIdle());
        datasource.setMaxActive(clickhouseJdbcConfig.getMaxActive());
        datasource.setMaxWait(clickhouseJdbcConfig.getMaxWait());
        datasource.setUsername(clickhouseJdbcConfig.getUsername());
        datasource.setPassword(clickhouseJdbcConfig.getPassword());
        return datasource;
    }

    @Bean(name = "sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 下划线转驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 指定数据源
        factoryBean.setDataSource(dataSource);
        // 指定mapper xml路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource[] mapperXml = resolver.getResources("classpath*:/mapper/clickhouse/*.xml");
        factoryBean.setTypeAliasesPackage("com.chance.entity.clickhouse");
        factoryBean.setMapperLocations(mapperXml);
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    @Bean(name = "transactionManager2")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource2") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate2")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory2") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
