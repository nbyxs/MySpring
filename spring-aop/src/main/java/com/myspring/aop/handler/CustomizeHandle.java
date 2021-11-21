package com.myspring.aop.handler;


import com.myspring.aop.methodList;
import com.myspring.aop.methodPool;
import com.myspring.exception.aopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
/**
 *  
 * 
 * @author nbyxs
 * @date 2021/11/17 11:35
 * @param 
 * @return
 * 反射实现
 */
public class CustomizeHandle implements InvocationHandler {
     private static List<methodList> lists=null;
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomizeHandle.class);

    private Object target;
    private  Object advise;

    public CustomizeHandle() {
    }

    public CustomizeHandle(Class clazz) {
        try {
            this.target = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

       lists= methodPool.getMethodLists();

       methodList methodList=null;

       for(methodList l:lists){
          // System.out.println(l.toString());
          // System.out.println(l.getMethod_pointcut().toString());

           if(l.getMethod_pointcut().toString().equals(method.toString())){
               methodList=l;
           }
       }

        //获取methodList信息 处理before 和 after
       if(methodList==null)throw new aopException("切面类为空！");
        before(methodList.getMethod_before(),methodList.getbClass());
        Object result = method.invoke(target, args);
        after(methodList.getMethod_after(),methodList.getbClass());

        LOGGER.info("proxy class={}", proxy.getClass());
        return result;
    }


    private void before(Method method_before,Class bClass) throws InvocationTargetException, IllegalAccessException {
      if(method_before==null)return ;
      //  System.out.println(bClass.toString()+"asaa");
        method_before.invoke(bClass, null);
    }

    private void after(Method method_after,Class bClass) throws InvocationTargetException, IllegalAccessException {
        if(method_after==null)return ;
      //  System.out.println(bClass.toString()+"   dd");
         method_after.invoke(bClass, null);
    }



}

