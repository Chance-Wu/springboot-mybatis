package com.chance.util;

import com.chance.entity.dto.UserDto;
import com.chance.mapper.mysql.UserMapper;
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
public class TestMybatis {

    public static void main(String[] args) throws Exception {
        // 1.读取配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2.根据配置创建sqlSessionFactoryBuilder
        SqlSessionFactory sqlSessionFactory = sessionFactoryBuilder.build(inputStream);
        // 3.打开一个sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4.使用sqlSession创建创建Mapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 5.使用代理对象执行方法
        List<UserDto> users = userMapper.queryAllUsers();
        for (UserDto user : users) {
            System.out.println(user);
        }
        sqlSession.close();
        inputStream.close();
    }
}
