package com.chance.service;

import com.chance.common.CommonRsp;

import javax.servlet.http.HttpServletRequest;

/**
 * ApiIdempotentTokenService
 * @author chance
 * @date 4/29/21 3:12 PM
 * @version 1.0
 */
public interface ApiIdempotentTokenService {

    CommonRsp<String> createToken();

    void checkToken(HttpServletRequest request);
}
