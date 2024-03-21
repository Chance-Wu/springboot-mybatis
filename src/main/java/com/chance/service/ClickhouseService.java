package com.chance.service;

import com.chance.entity.clickhouse.Menu;

import java.util.List;

/**
 * @author: chance
 * @date: 2024/3/20 15:04
 * @since: 1.0
 */
public interface ClickhouseService {
    List<Menu> queryMenusByDate(String date);

    void insert();
}
