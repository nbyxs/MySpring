package com.example.myspring;

import com.example.myspring.bean.User;
import com.example.myspring.exception.creatException;
import com.example.myspring.exception.requestException;
import com.example.myspring.ioc.BeansPool;
import org.apache.catalina.LifecycleException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SStart {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, creatException, requestException, LifecycleException {
        Ms.run();
        User user= (User) BeansPool.getBeansPool().getMap().get(User.class);
        System.out.println(user.toString());












































    }
}
