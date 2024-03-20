package com.chance.service.impl;

import com.chance.entity.clickhouse.Menu;
import com.chance.mapper.clickhouse.MenuMapper;
import com.chance.service.ClickhouseService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: chance
 * @date: 2024/3/20 15:04
 * @since: 1.0
 */
@Service
public class ClickhouseServiceImpl implements ClickhouseService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> queryMenusByDate(String date) {
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("date", "1900-04-15");
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus;
    }
}
