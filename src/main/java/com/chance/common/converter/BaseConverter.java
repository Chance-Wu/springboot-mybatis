package com.chance.common.converter;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Stream;

/**
 * @Description: BaseConverter
 * @Author: chance
 * @Date: 2020-09-17 13:01
 * @Version 1.0
 */
@MapperConfig
public interface BaseConverter<S, T> {

    /**
     * 映射同名属性
     */
//    @Mapping(ta rget = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mappings({})
    T sourceToTarget(S source);

    /**
     * 反向，映射同名属性
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    S targetToSource(T var1);

    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<T> sourceToTarget(List<S> var1);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    List<S> targetToSource(List<T> var1);

    /**
     * 映射同名属性，集合流形式
     */
    List<T> sourceToTarget(Stream<S> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<S> targetToSource(Stream<T> stream);
}
