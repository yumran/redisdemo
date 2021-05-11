package com.sang.redisdemo.service;

import javax.servlet.http.HttpServletRequest;

public interface ApiIdempotentTokenService<T> {

    T createToken();

    void checkToken(HttpServletRequest request);

}
