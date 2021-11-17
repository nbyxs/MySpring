package com.example.myspring.aop;

import com.example.myspring.aop.annotations.After;
import com.example.myspring.aop.annotations.Aspect;
import com.example.myspring.aop.annotations.Before;
import com.example.myspring.exception.aopException;
import com.example.myspring.utils.PackageListUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;


public class aopScanner {

    public static  void init() throws aopException {
        //获取 class
        List<Class> classes= PackageListUtils.getAllClass();

        //获取 切面
        List<methodList> methodLists=methodPool.getMethodLists();
        for(Class c:classes){
            if(c.isAnnotationPresent(Aspect.class)){
                Method[] methods=c.getMethods();
                //查询class标注Before和After注解的方法
                Method method_before=search(methods, Before.class);
                Method method_after=search(methods, After.class);
                if(method_before==null&&method_after==null)continue;


                //获取方法中的内容（规定内容必须相同，为对应要处理的方法）
                String name1=search(method_before,Before.class);
                String name2=search(method_after,After.class);
                System.out.println(name1+"  "+name2);

                if(!name1.equals(name2)){
                    throw  new aopException("一个切面类的方法只能对同一个方法进行切面处理!");
                }
               methodList method_point= search(classes,name1,method_before,method_after,c);

                //查询对应方法是否存在
                if(method_point==null){
                    throw new aopException("对应方法不存在！");
                }
                //判断方法是否有重复处理
                if(methodLists.contains(method_point)){
                    throw new aopException("多个切面类对同一个方法进行处理！");
                }
                //添加方法和切面类
                methodLists.add(method_point);
              //  System.out.println(method_point.toString());
            }
        }
        methodPool.setMethodLists(methodLists);
       // System.out.println(methodPool.getMethodLists().toString());

    }



    private static methodList search(List<Class> classes, String name1,Method method_before, Method method_after,Class bClass) {
       // System.out.println(method_before.toString());
        for(Class c:classes){
            Method[] methods=c.getMethods();
            for(Method m:methods){
               // System.out.println(m.toString()+" -");
                if(m.toString().equals(name1)){

                    return new methodList(m,method_before,method_after,c,bClass);
//                    System.out.println("search  "+methodList.toString());
                }
            }

        }
        return null;
    }


    private static Method search(Method[] methods, Class className) {
        for(Method m:methods){
            if(m.isAnnotationPresent(className))return  m;
        }
        return null;
    }
    private static String search(Method method, Class className) {
        if(method==null)return null;
        Annotation[] annotations=method.getAnnotations();
        for(Annotation a:annotations){
            if(a.annotationType().equals(className)) {


                if (className.equals(After.class)) {
                    After after=(After) a;
                    return  after.value();

                }
                else {
                    Before before=(Before) a;
                    return before.value();
                }
            }
        }
        return null;
    }
}
