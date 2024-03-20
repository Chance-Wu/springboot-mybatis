package com.chance.entity.clickhouse;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: chance
 * @date: 2024/3/20 14:52
 * @since: 1.0
 */
@Data
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    private Long id;

    private String name;

    private String sponsor;

    private String event;

    private String venue;

    private String place;

    @Column(name = "physical_description")
    private String physicalDescription;

    private String occasion;

    private String notes;

    @Column(name = "call_number")
    private String callNumber;

    private String keywords;

    private String language;

    private String date;

    private String location;

    @Column(name = "location_type")
    private String locationType;

    private String currency;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    private String status;

    @Column(name = "page_count")
    private Long pageCount;

    @Column(name = "dish_count")
    private Long dishCount;
}
