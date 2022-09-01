package com.chance.util;

import com.chance.entity.User;
import com.chance.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @author: chance
 * @date: 2022/8/31 10:11
 * @since: 1.0
 */
public class Test {

    public static void main(String[] args) throws Exception {
        // 1.读取配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2.根据配置创建sqlSessionFactoryBuilder
        SqlSessionFactory sqlSessionFactory = sessionFactoryBuilder.build(inputStream);
        // 3.打开一个sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.queryAllUsers();
        System.out.println(users);
    }
}
