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
        return menuMapper.selectByExample(example);
    }

    @Override
    public void insert() {
        Menu menu = new Menu();
        menu.setId(99999L);
        menu.setName("WCY");
        menu.setSponsor("LOHG BEACH HOTEL");
        menu.setEvent("DINNER");
        menu.setVenue("COMMERCIAL");
        menu.setPlace("LONG BEACH,L.I.,[NY&]");
        menu.setPhysicalDescription("CARD;ILLUS;5X8;");
        menu.setOccasion("DAILY;");
        menu.setNotes("ANIMAL FIGURE;");
        menu.setCallNumber("1900-4064");
        menu.setKeywords("WCY");
        menu.setLanguage("ENGLISH");
        menu.setDate("1900-08-16");
        menu.setLocation("Long Beach Hotel");
        menu.setLocationType("Long Beach Hotel");
        menu.setCurrency("Dollars");
        menu.setCurrencySymbol("$");
        menu.setStatus("complete");
        menu.setPageCount(10L);
        menu.setDishCount(10L);
        menuMapper.insert(menu);
    }
}
