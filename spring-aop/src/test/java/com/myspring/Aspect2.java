package com.myspring;

import com.myspring.aop.annotations.After;
import com.myspring.aop.annotations.Aspect;
import com.myspring.aop.annotations.Before;


@Aspect
public class Aspect2 {

    @Before("public abstract void com.myspring.dao.ISubject.execute()")
    public static void before() {
        System.out.println("[Aspect2] before advise");
    }

    @After("public abstract void com.myspring.dao.ISubject.execute()")
    public static void after() {
        System.out.println("[Aspect2] after advise");
    }
}