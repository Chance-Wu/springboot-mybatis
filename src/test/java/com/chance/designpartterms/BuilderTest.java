package com.chance.designpartterms;

import com.chance.designpatterns.builder.ReportConfig;
import com.chance.designpatterns.builder.ReportConfig2;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

/**
 * @author chance
 * @date 2024/12/30 15:03
 * @since 1.0
 */
public class BuilderTest {

    @Test
    public void test() {
        ReportConfig reportConfig = new ReportConfig.Builder()
                .title("report2025")
                .startDate(new Date())
                .outputFormat("list")
                .recipients(Arrays.asList("1000$", "2000$", "3000$"))
                .build();
        System.out.println(reportConfig);

        ReportConfig2 reportConfig2 = ReportConfig2.builder().title("report2025")
                .startDate(new Date())
                .outputFormat("list")
                .recipients(Arrays.asList("1000$", "2000$", "3000$"))
                .build();
        System.out.println(reportConfig2);
    }
}
