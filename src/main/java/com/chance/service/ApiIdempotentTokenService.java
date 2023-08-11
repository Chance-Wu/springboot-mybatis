package com.chance.service;

import com.chance.common.CommonRsp;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: ApiIdempotentTokenService
 * @Author: chance
 * @Date: 4/29/21 3:12 PM
 * @Version 1.0
 */
public interface ApiIdempotentTokenService {

    CommonRsp createToken();

    void checkToken(HttpServletRequest request);
}
