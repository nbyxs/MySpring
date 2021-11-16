package com.example.myspring.aop;
import com.example.myspring.aop.dao.daoImp.ISubjectImpl;
import com.example.myspring.aop.dao.daoImp.d;
import com.example.myspring.aop.handler.CustomizeHandle;
import com.example.myspring.exception.aopException;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class test {

    public static void main(String[] args) throws aopException {
        aopScanner.init();
        System.out.println("________________");
        CustomizeHandle customizeHandle=new CustomizeHandle(d.class);

        ISubject subject=(ISubject)Proxy.newProxyInstance(ISubject.class.getClassLoader(),new Class[]{ISubject.class}, customizeHandle);
        subject.execute();

    }


}
