package com.myspring.exception;

import java.rmi.ServerException;
/**
 *
 *
 * @author nbyxs
 * @date 2021/11/21 23:13
 * @param
 * @return 请求异常处理
 */
public class requestException extends ServerException {
    public requestException(String message) {
        super(message);
    }
}
