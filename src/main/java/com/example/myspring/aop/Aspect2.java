package com.example.myspring.aop;

import com.example.myspring.aop.annotations.After;
import com.example.myspring.aop.annotations.Aspect;
import com.example.myspring.aop.annotations.Before;

@Aspect
public class Aspect2 {

    @Before("public abstract void com.example.myspring.aop.ISubject.execute()")
    public void before() {
        System.out.println("[Aspect2] before advise");
    }







    @After("public abstract void com.example.myspring.aop.ISubject.execute()")
    public void after() {
        System.out.println("[Aspect2] after advise");
    }
}