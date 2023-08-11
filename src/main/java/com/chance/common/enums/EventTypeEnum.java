package com.chance.common.enums;

import com.chance.service.UnifiedService;

/**
 * <p> 事件类型枚举 </p>
 *
 * @author chance
 * @date 2023/8/10 14:47
 * @since 1.0
 */
public enum EventTypeEnum {
    MEMBER_EVENT("memberEvent", UnifiedService.MEMBER_SERVICE, "会员事件"),
    COUPON_EVENT("couponEvent", UnifiedService.COUPON_SERVICE, "会卡券事件"),
    ;

    private String eventType;
    private String serviceName;
    private String desc;

    EventTypeEnum(String eventType, String serviceName, String desc) {
        this.eventType = eventType;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    public String getEventType() {
        return eventType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDesc() {
        return desc;
    }

    public static String getServiceNameByType(String eventType) {
        for (EventTypeEnum value : EventTypeEnum.values()) {
            if (value.getEventType() == eventType) {
                return value.getServiceName();
            }
        }
        return null;
    }
}
