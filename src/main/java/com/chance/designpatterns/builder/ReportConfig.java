package com.chance.designpatterns.builder;

import java.util.Date;
import java.util.List;

/**
 * @author chance
 * @date 2024/12/30 14:50
 * @since 1.0
 */
public class ReportConfig {

    // 1.外部类的字段必须是 final（保证不可变性）
    private final String title;
    private final Date startDate;
    private final String outputFormat;
    private final List<String> recipients;

    /**
     * 2.外部类只提供一个 private 构造函数，强制通过 Builder 创建
     */
    private ReportConfig(Builder builder) {
        this.title = builder.title;
        this.startDate = builder.startDate;
        this.outputFormat = builder.outputFormat;
        this.recipients = builder.recipients;
    }

    /**
     * 3.静态内部 Builder 类
     */
    public static class Builder {

        // Builder 类的字段不需要是 final
        private String title;
        private Date startDate;
        private String outputFormat;
        private List<String> recipients;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder outputFormat(String outputFormat) {
            this.outputFormat = outputFormat;
            return this;
        }

        public Builder recipients(List<String> recipients) {
            this.recipients = recipients;
            return this;
        }

        public ReportConfig build() {
            return new ReportConfig(this);
        }
    }
}
