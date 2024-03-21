package com.chance.controller.clickhouse;

import com.chance.entity.clickhouse.Menu;
import com.chance.service.ClickhouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: chance
 * @date: 2024/3/20 15:02
 * @since: 1.0
 */
@Slf4j
@Api("ClickhouseController")
@RestController
@RequestMapping("/clickhouse")
public class ClickHouseController {

    @Autowired
    private ClickhouseService clickhouseService;

    @ApiOperation(value = "query", notes = "查询")
    @RequestMapping("/query")
    public String queryMenus(@RequestParam("date") String date) {
        List<Menu> menus = clickhouseService.queryMenusByDate(date);
        return "success";
    }

    @ApiOperation(value = "insert", notes = "写入")
    @RequestMapping("/insert")
    public String insert() {
        clickhouseService.insert();
        return "success";
    }
}
