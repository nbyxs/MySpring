package com.myspring.aop;

import java.lang.reflect.Method;
/**
 *
 *
 * @author nbyxs
 * @date 2021/11/17 11:39
 * @param
 * @return
 * 规定：对同一方法进行切入的before after需在一个类中，且要static修饰（省去为类生成实例）
 */
public class methodList {
    private Method method_pointcut;//切入的方法
    private Method method_before;//before切入点
    private  Method method_after;//after切入点
    Class aClass;//需要切入方法对应类
    Class bClass;//before after对应类（反射执行需要对应类）

    public methodList(Method method_pointcut, Method method_before, Method method_after, Class aClass,Class bClass) {
        this.method_pointcut = method_pointcut;
        this.method_before = method_before;
        this.method_after = method_after;
        this.aClass = aClass;
        this.bClass=bClass;
    }



    public methodList() {
    }

    @Override
    public String toString() {
        return "methodList{" +
                "method_pointcut=" + method_pointcut +
                ", method_before=" + method_before +
                ", method_after=" + method_after +
                ", aClass=" + aClass +
                '}';
    }

    public Method getMethod_pointcut() {
        return method_pointcut;
    }

    public void setMethod_pointcut(Method method_pointcut) {
        this.method_pointcut = method_pointcut;
    }

    public Method getMethod_before() {
        return method_before;
    }

    public void setMethod_before(Method method_before) {
        this.method_before = method_before;
    }

    public Method getMethod_after() {
        return method_after;
    }

    public void setMethod_after(Method method_after) {
        this.method_after = method_after;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public Class getbClass() {
        return bClass;
    }

    public void setbClass(Class bClass) {
        this.bClass = bClass;
    }
}
