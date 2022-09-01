//package com.chance.config;
//
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
///**
// * <p>
// *
// * <p>
// *
// * @author chance
// * @since 2020-08-22
// */
//@Configuration
//@EnableTransactionManagement
//@MapperScan("com.chance.mapper")
//public class MybatisPlusConfig {
//
//    /**
//     * 性能分析拦截器，不建议生产使用 用来观察 SQL 执行情况及执行时长, 默认dev,staging 环境开启
//     * @author fxbin
//     * @return com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
//     */
////    @Bean
////    @Profile({"dev", "staging"})
////    public PerformanceInterceptor performanceInterceptor(){
////
////        //启用性能分析插件, SQL是否格式化 默认false,此处开启
////        return new PerformanceInterceptor().setFormat(true);
////    }
//
//
//    /**
//     * 分页插件
//     * @author fxbin
//     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//}
