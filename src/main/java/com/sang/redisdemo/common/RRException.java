package com.sang.redisdemo.common;

public class RRException extends RuntimeException {

    private String msg;
    private Integer code = 500;

    public RRException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

}
