package com.chance.component;

import com.chance.common.ResultCode;
import com.chance.common.enums.EventTypeEnum;
import com.chance.common.exception.BizException;
import com.chance.service.UnifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> EventContextAdaptor </p>
 *
 * @author chance
 * @date 2023/8/10 14:52
 * @since 1.0
 */
@Component
public class EventContextAdaptor {

    /**
     * @Autowired 标注作用于 Map 类型时，如果 Map 的 key 为 String 类型，则 Spring 会将容器中所有类型符合 Map 的 value 对应的类型的 Bean 增加进来，用 Bean 的 id 或 name 作为 Map 的 key。
     */
    @Autowired
    public Map<String, UnifiedService> unifiedServiceMap = new ConcurrentHashMap<>();

    /**
     * 根据事件类型获取具体事件实现类
     *
     * @param eventType
     * @return
     */
    public UnifiedService getEventServiceByType(String eventType) {
        String serviceName = EventTypeEnum.getServiceNameByType(eventType);
        if (StringUtils.isEmpty(serviceName)) {
            throw new BizException(ResultCode.FAIL.getCode(), "eventType:'" + eventType + "' does not exists!");
        }
        UnifiedService strategy = unifiedServiceMap.get(serviceName);
        if (strategy == null) {
            throw new BizException(ResultCode.FAIL.getCode(), "No EventService Matched, EventServiceName : " + eventType);
        }
        return strategy;
    }
}
