package com.example.myspring;

import com.example.myspring.bean.User;
import com.example.myspring.exception.creatException;
import com.example.myspring.ioc.BeansPool;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SStart {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, creatException {
        Ms.run();
        User user= (User) BeansPool.getBeansPool().getMap().get(User.class);
        System.out.println(user.toString());












































    }
}
