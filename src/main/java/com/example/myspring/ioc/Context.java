package com.example.myspring.ioc;

import com.example.myspring.exception.creatException;
import com.example.myspring.ioc.annotations.Bean;
import com.example.myspring.ioc.annotations.Component;
import com.example.myspring.ioc.annotations.Configuration;
import com.example.myspring.ioc.annotations.Resource;
import com.example.myspring.utils.PackageListUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Bean、@Resource、@Component、@AutoWired容易弄混，在此区分一下。
 *
 * @Bean一般与@Configuration注解配合使用，相当于xml配置文件中的<Beans>与<Bean>的关系。@Bean用于方法上。
 *
 * @Component一般用于类上
 *
 * @Resource一般用于查找资源，用于字段变量或setter方法上，按照名称装载
 *
 * @AutoWired用于字段变量上，按照类型装载
 *
 * @controller用于url映射
 *
 * @Service用于逻辑处理

 */
@Slf4j
public class Context {
    static  BeansPool beansPool=BeansPool.getInstance();
    public static void init() throws IllegalAccessException, InstantiationException, InvocationTargetException, creatException {

        List<Class> list= PackageListUtils.getAllClass();

        //Component和Configuration在类上使用
        for(Class c:list){
            if(c.isAnnotationPresent(Component.class)||c.isAnnotationPresent(Configuration.class)){
                log.info("Created bean for [{}]", c);
               beansPool.setObject(c,c.newInstance());
            }
        }
        System.out.println("_______________");
        for(Map.Entry<Class, Object> map:beansPool.getMap().entrySet()){
            System.out.println(map.getKey()+"  "+map.getValue());
        }
        System.out.println("_______________");
        //Resource 作用于字段
        for(Class c:list){
            //获取class类的所有字段
            Field[] fields=c.getDeclaredFields();
            for(Field f:fields){
                if(f.isAnnotationPresent(Resource.class)){
                    Object classObject = beansPool.getObject(c);
                    Object fieldObject = beansPool.getObject(f.getType());
                    f.setAccessible(true);
                    f.set(classObject, fieldObject);
                    log.info("Inject bean [{}] type [{}] into [{}]", fieldObject, f.getType(), c);

                }
            }
        }
        //Bean和Configuration结合 Bean作用于配置类方法返回一个类对象
        for(Class c:list) {
            if (c.isAnnotationPresent(Configuration.class)) {
                Method[] methods = c.getDeclaredMethods();

                for (Method m : methods) {


                    if (m.isAnnotationPresent(Bean.class)){


                           Object classObject =beansPool.getObject(c);
                           Object o = m.invoke(classObject);


                        /**
                         * 配置类需返回生成对象
                         */
                        if(o==null)throw new creatException("Component类"+c.getName()+"标注的Bean方法："+m.getName()+"未返回有效对象");
                            String class_name=o.getClass().getName();


                        /**
                         * 配置类中不能重复对同一个类生成多个对象
                         *  @Bean
                         *     public  User init(){
                         *
                         *        return new User(1,"111");
                         *     }
                         *     @Bean
                         *     public  User init1(){
                         *
                         *         return new User(1,"211");
                         *     }
                         */
                           if(BeansPool.getInstance().getList_register().contains(class_name))
                               throw new creatException("多个配置类对同一个类"+class_name+"生成多个对象！");

                           log.info("Created bean for [{}] by method [{}]", class_name, m.getName());
                           beansPool.setObject(o.getClass(), o);
                           beansPool.setList_register(class_name);



                    }
                }
            }
        }
        System.out.println("_______________");
        for(Map.Entry<Class, Object> map:BeansPool.getInstance().getMap().entrySet()){
            System.out.println(map.getKey()+"  "+map.getValue());
        }
        System.out.println("_______________");
    }
}
