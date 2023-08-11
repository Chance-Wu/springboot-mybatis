package com.chance.service.impl;

import com.chance.service.UnifiedService;
import org.springframework.stereotype.Service;

/**
 * <p> 卡券事件实现 </p>
 *
 * @author chance
 * @date 2023/8/10 14:44
 * @since 1.0
 */
@Service(UnifiedService.COUPON_SERVICE)
public class CouponEventServiceImpl implements UnifiedService {


    @Override
    public String executeEvent() {
        return "couponEvent";
    }
}
