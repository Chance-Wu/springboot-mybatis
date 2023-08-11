package com.chance.service;

/**
 * <p> 统一行为接口 </p>
 *
 * @author chance
 * @date 2023/8/10 14:42
 * @since 1.0
 */
public interface UnifiedService {

    String MEMBER_SERVICE = "MEMBER";

    String COUPON_SERVICE = "COUPON";

    /**
     * 执行事件
     */
    String executeEvent();

}
