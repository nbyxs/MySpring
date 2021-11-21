package com.myspring;

import com.myspring.bean.User;
import com.myspring.exception.creatException;
import com.myspring.exception.requestException;
import com.myspring.ioc.BeansPool;
import org.apache.catalina.LifecycleException;

import java.lang.reflect.InvocationTargetException;

/**
 * 启动类
 */
public class Start {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, creatException, requestException, LifecycleException, ClassNotFoundException {
        Ms.run();
        User user = (User) BeansPool.getBeansPool().getMap().get(User.class);
        System.out.println(user.toString());

    }
}
