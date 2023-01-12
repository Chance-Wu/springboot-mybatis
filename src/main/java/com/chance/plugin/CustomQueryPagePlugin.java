package com.chance.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @description: CustomQueryPagePlugin
 * @author: chance
 * @date: 2022/11/11 08:55
 * @since: 1.0
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class CustomQueryPagePlugin implements Interceptor {

    /**
     * 覆盖原有方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("成功拦截了Executor的query方法，在这里可以做点什么");
        //调用原方法
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //把被拦截对象生成一个代理对象
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //可以自定义一些属性
        System.out.println("自定义属性:userName->" + properties.getProperty("userName"));
    }
}
