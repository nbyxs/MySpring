package com.example.myspring;

import com.example.myspring.exception.creatException;
import com.example.myspring.ioc.Context;

import java.lang.reflect.InvocationTargetException;

public class Ms {
    public static void run() throws IllegalAccessException, InvocationTargetException, InstantiationException, creatException {
        Context.init();

    }
}
