package com.example.myspring.aop.handler;


import com.example.myspring.aop.Aspect2;
import com.example.myspring.aop.methodList;
import com.example.myspring.aop.methodPool;
import com.example.myspring.exception.aopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CustomizeHandle implements InvocationHandler {
     private static List<methodList> lists=null;
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomizeHandle.class);

    private Object target;

    public CustomizeHandle() {
    }

    public CustomizeHandle(Class clazz) {
        try {
            this.target = clazz.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("InstantiationException", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("IllegalAccessException",e);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

       lists= methodPool.getMethodLists();

       methodList methodList=null;

       for(methodList l:lists){
           System.out.println(l.toString());
           System.out.println(l.getMethod_pointcut().toString());

           if(l.getMethod_pointcut().toString().equals(method.toString())){
               methodList=l;
           }
       }

        //获取methodList信息 处理before 和 after
       if(methodList==null)throw new aopException("切面类为空！");
        before(methodList.getMethod_before());
        Object result = method.invoke(target, args);
        after(methodList.getMethod_after());

        LOGGER.info("proxy class={}", proxy.getClass());
        return result;
    }


    private Object before(Method method_before) throws InvocationTargetException, IllegalAccessException {
      if(method_before==null)return null;
       return method_before.invoke(method_before.getClass(), (Object) method_before.getParameters());
    }

    private Object after(Method method_after) throws InvocationTargetException, IllegalAccessException {
        if(method_after==null)return null;
     return    method_after.invoke(method_after.getClass(),method_after.getParameters());
    }



}

