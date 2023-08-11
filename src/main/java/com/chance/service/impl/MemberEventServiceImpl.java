package com.chance.service.impl;

import com.chance.service.UnifiedService;
import org.springframework.stereotype.Service;

/**
 * <p> 会员事件实现 </p>
 *
 * @author chance
 * @date 2023/8/10 14:43
 * @since 1.0
 */
@Service(UnifiedService.MEMBER_SERVICE)
public class MemberEventServiceImpl implements UnifiedService {

    @Override
    public String executeEvent() {
        return "memberEvent";
    }
}
