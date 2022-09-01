package com.chance.service.impl;

import com.chance.common.api.CommonRsp;
import com.chance.common.api.ResultCode;
import com.chance.common.exception.BizException;
import com.chance.component.RedisUtils;
import com.chance.service.ApiIdempotentTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Description: ApiIdempotentTokenServiceImpl
 * @Author: chance
 * @Date: 4/29/21 3:13 PM
 * @Version 1.0
 */
@Service
public class ApiIdempotentTokenServiceImpl implements ApiIdempotentTokenService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public CommonRsp createToken() {
        CommonRsp rsp = new CommonRsp<>();
        String uuid = UUID.randomUUID().toString();
        StringBuilder token = new StringBuilder();
        token.append("API_IDEMPOTENT_TOKEN:").append(uuid);
        redisUtils.set(token.toString(), token.toString(), 5 * 60);
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
            //            // parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                throw new BizException(ResultCode.ILLEGAL_ARGUMENT.getCode(),ResultCode.ILLEGAL_ARGUMENT.getMessage());
            }
        }

        if (!redisUtils.exists(token)) {
            throw new BizException(ResultCode.REPETITIVE_OPERATION.getCode(),ResultCode.REPETITIVE_OPERATION.getMessage());
        }

        Boolean del = redisUtils.del(token);
        if (!del) {
            throw new BizException(ResultCode.REPETITIVE_OPERATION.getCode(),ResultCode.REPETITIVE_OPERATION.getMessage());
        }
    }
}
