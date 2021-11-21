package com.myspring;
import com.myspring.aop.aopScanner;
import com.myspring.aop.handler.CustomizeHandle;
import com.myspring.dao.ISubject;
import com.myspring.daoImp.ISubJectImpl2;
import com.myspring.exception.aopException;

import java.lang.reflect.Proxy;

/**
 * 测试aop
 */
public class test_aop {

    public static void main(String[] args) throws aopException {
        aopScanner.init();
        System.out.println("________________");
        CustomizeHandle customizeHandle=new CustomizeHandle(ISubJectImpl2.class);

        ISubject subject=(ISubject)Proxy.newProxyInstance(ISubject.class.getClassLoader(),new Class[]{ISubject.class}, customizeHandle);
        subject.execute();

    }


}
