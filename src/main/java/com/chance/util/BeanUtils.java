package com.chance.util;

/**
 * <p> BeanUtils </p>
 *
 * @author chance
 * @date 2023/5/3 14:13
 * @since 1.0
 */

import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * BeanUtils用于属性拷贝
 */
public class BeanUtils {

    /**
     * Bean属性拷贝
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * po与vo容器互转
     *
     * @param sourceCollection
     * @param clazz
     * @return
     */
    public static Collection<?> collectionPoVoSwitch(Collection<?> sourceCollection, Class<?> clazz) {

        if (null == sourceCollection) {
            return Collections.EMPTY_LIST;
        }

        List<Object> targetList = new ArrayList<>();
        try {
            for (Object source : sourceCollection) {
                if (source == null) continue;
                Object target = clazz.newInstance();
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return targetList;
    }

    /**
     * Bean属性拷贝，不拷贝值为空的属性.
     * <p>
     * 当属性为下列情况时，不进行copy:
     * <p>
     * 1. 当该属性为 hashCode、serialVersionUID
     * <p>
     * <p>
     * 注:
     * 不支持复杂多级bean对象的Copy
     * 复杂对象请使用dozer进行对应的属性对应设置
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesNotNull(Object source, Object target) {
        Field[] fileds = target.getClass().getDeclaredFields();
        for (int i = 0; i < fileds.length; i++) {
            try {
                String filedName = fileds[i].getName();
                char firstWord = filedName.charAt(0);
                if (java.lang.Character.isLowerCase(firstWord) && !filedName.equals("hashCode")
                        && !filedName.equals("serialVersionUID")) {

                    String setMethodName = "set" + String.valueOf(firstWord).toUpperCase()
                            + filedName.substring(1, filedName.length());
                    String getMethodName = "get" + String.valueOf(firstWord).toUpperCase()
                            + filedName.substring(1, filedName.length());
                    Method getMethod = source.getClass().getMethod(getMethodName, new Class[]{});
                    if (getMethod != null) {
                        Object getValue = getMethod.invoke(source, new Object[]{});
                        if (getValue != null && getMethod.getReturnType() != null) {
                            Method setMethod = target.getClass().getMethod(setMethodName,
                                    new Class[]{getMethod.getReturnType()});
                            if (setMethod != null) {
                                setMethod.invoke(target, new Object[]{getValue});
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Bean属性拷贝，不拷贝值为空的属性， 适合用于拷贝至非空的对象.
     * <p>
     * 当属性为下列情况时，不进行copy:
     * <p>
     * 当该属性为 id、hashCode、serialVersionUID
     * <p>
     * <p>
     * 注:
     * 不支持复杂多级bean对象的Copy
     * 复杂对象请使用dozer进行对应的属性对应设置
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesLessToMore(Object source, Object target) {

        Field[] fileds = target.getClass().getDeclaredFields();
        Field[] sourceFile = source.getClass().getDeclaredFields();
        List<String> sourceFiledNameList = new ArrayList<String>();
        for (int i = 0; i < sourceFile.length; i++) {
            String sourcefiledName = sourceFile[i].getName();
            sourceFiledNameList.add(sourcefiledName);
        }
        for (int i = 0; i < fileds.length; i++) {
            try {
                String filedName = fileds[i].getName();
                if (!sourceFiledNameList.contains(filedName))
                    continue;

                char firstWord = filedName.charAt(0);
                if (java.lang.Character.isLowerCase(firstWord) && !filedName.equals("id")
                        && !filedName.equals("hashCode") && !filedName.equals("serialVersionUID")) {

                    String setMethodName = "set" + String.valueOf(firstWord).toUpperCase()
                            + filedName.substring(1, filedName.length());
                    String getMethodName = "get" + String.valueOf(firstWord).toUpperCase()
                            + filedName.substring(1, filedName.length());
                    Method getMethod = source.getClass().getMethod(getMethodName, new Class[]{});
                    if (getMethod != null) {
                        Object getValue = getMethod.invoke(source, new Object[]{});
                        if (getValue != null && getMethod.getReturnType() != null
                                && !StringUtils.isEmpty(getValue.toString())) {
                            Method setMethod = target.getClass().getMethod(setMethodName,
                                    new Class[]{getMethod.getReturnType()});
                            if (setMethod != null) {
                                setMethod.invoke(target, new Object[]{getValue});
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用Introspector和PropertyDescriptor 将Bean --> Map
     *
     * @param obj Source object
     * @param map Map
     * @return Map
     */
    public static void transBean2Map(Object obj, Map<String, Object> map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法

                }
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);

                map.put(key, value);
            }


        } catch (
                Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
    }
}