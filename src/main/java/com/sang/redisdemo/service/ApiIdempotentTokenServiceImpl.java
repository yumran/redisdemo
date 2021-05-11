package com.sang.redisdemo.service;

import com.sang.redisdemo.common.Constant;
import com.sang.redisdemo.common.RRException;
import com.sang.redisdemo.common.ResponseCode;
import com.sang.redisdemo.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service("apiIdempotentTokenService")
public class ApiIdempotentTokenServiceImpl implements ApiIdempotentTokenService<ResponseEntity<String>> {

    private static final String API_IDEMPOTENT_TOKEN_NAME = "apiIdempotentToken";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseEntity<String> createToken() {
        String str = UUID.randomUUID().toString();
        StringBuilder token = new StringBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);
        redisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_FIVE_MINUTE);
        return new ResponseEntity<String>(token.toString(), HttpStatus.OK);
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(API_IDEMPOTENT_TOKEN_NAME);
        // header 中不存在token
        if(StringUtils.isBlank(token)) {
            token = request.getParameter(API_IDEMPOTENT_TOKEN_NAME);
            // parameter中也不存在token
            if(StringUtils.isBlank(token)) {
                throw new RRException(ResponseCode.ILLEGAL_ARGUMENT.getMsg(), ResponseCode.ILLEGAL_ARGUMENT.getCode());
            }
        }

        if(!redisUtil.exists(token)) {
            throw new RRException(ResponseCode.REPETITIVE_OPERATION.getMsg(), ResponseCode.REPETITIVE_OPERATION.getCode());
        }

        Boolean del = redisUtil.del(token);
        if(!del) {
            throw new RRException(ResponseCode.REPETITIVE_OPERATION.getMsg(), ResponseCode.REPETITIVE_OPERATION.getCode());
        }
    }
}
