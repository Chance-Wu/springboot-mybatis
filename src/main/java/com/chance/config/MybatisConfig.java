package com.chance.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.chance.plugin.CustomQueryPagePlugin;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <p>
 * Mybatis配置类
 * <p>
 *
 * @author chance
 * @since 2020-08-15
 */
@MapperScan(basePackages = {"com.chance.mapper"}, sqlSessionFactoryRef = "sessionFactory1")
@ComponentScan
@EnableTransactionManagement
@Configuration
public class MybatisConfig {

    @Bean("dataSource1")
    //@Primary //当有多个相同类型的bean时，使用@Primary来赋予bean更高的优先级
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 创建主数据源的事务管理
     */
    @Bean(name = "transactionManager1")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("dataSource1") DataSource dataSource1) {
        return new DataSourceTransactionManager(dataSource1);
    }

    /**
     * 创建Mybatis的连接会话工厂实例
     */
    @Bean(name = "sessionFactory1")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource1) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource1);
        factoryBean.setTypeAliasesPackage("com.chance.entity");
        factoryBean.setTypeHandlersPackage("com.chance.handler");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 下划线转驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        // 日志具体实现
        configuration.setLogImpl(StdOutImpl.class);
        configuration.setCacheEnabled(false);
        factoryBean.setConfiguration(configuration);

        //分页插件
        CustomQueryPagePlugin queryPagePlugin = new CustomQueryPagePlugin();
        factoryBean.setPlugins(new Interceptor[]{queryPagePlugin});

        //添加XML目录（mapper的xml形式文件位置必须要配置）
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
        return factoryBean.getObject();
    }
}
