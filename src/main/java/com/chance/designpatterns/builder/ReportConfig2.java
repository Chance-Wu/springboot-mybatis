package com.chance.designpatterns.builder;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;

/**
 * @author chance
 * @date 2024/12/30 14:50
 * @since 1.0
 */
@Builder
@Value
public class ReportConfig2 {

    private String title;
    private Date startDate;
    private String outputFormat;
    private List<String> recipients;
}
