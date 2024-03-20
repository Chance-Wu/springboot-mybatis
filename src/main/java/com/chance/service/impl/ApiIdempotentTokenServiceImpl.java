package com.chance.service.impl;

import com.chance.common.CommonRsp;
import com.chance.common.ResultCode;
import com.chance.common.exception.BizException;
import com.chance.component.RedisUtils;
import com.chance.service.ApiIdempotentTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * ApiIdempotentTokenServiceImpl
 *
 * @author chance
 * @version 1.0
 * @date 4/29/21 3:13 PM
 */
@Service
public class ApiIdempotentTokenServiceImpl implements ApiIdempotentTokenService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public CommonRsp<String> createToken() {
        CommonRsp<String> rsp = new CommonRsp<>();
        String uuid = UUID.randomUUID().toString();
        StringBuilder token = new StringBuilder();
        token.append("API_IDEMPOTENT_TOKEN:").append(uuid);
        redisUtils.set(token.toString(), token.toString(), 5 * 60L);
        rsp.setCode(ResultCode.SUCCESS.getCode());
        rsp.setMessage(ResultCode.SUCCESS.getMessage());
        rsp.setBody(token.toString());
        return rsp;
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader("apiIdempotentToken");

        // header中不存在token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("apiIdempotentToken");
            // parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                throw new BizException(ResultCode.ILLEGAL_ARGUMENT.getCode(), ResultCode.ILLEGAL_ARGUMENT.getMessage());
            }
        }

        if (Boolean.FALSE.equals(redisUtils.exists(token))) {
            throw new BizException(ResultCode.REPETITIVE_OPERATION.getCode(), ResultCode.REPETITIVE_OPERATION.getMessage());
        }

        Boolean del = redisUtils.del(token);
        if (Boolean.FALSE.equals(del)) {
            throw new BizException(ResultCode.REPETITIVE_OPERATION.getCode(), ResultCode.REPETITIVE_OPERATION.getMessage());
        }
    }
}
