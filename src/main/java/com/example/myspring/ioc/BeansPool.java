package com.example.myspring.ioc;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单例模式创建BeansPool
 */
public class BeansPool {

    private static BeansPool beansPool;
    // 根据类型保存对象
    private Map<Class, Object> map = new HashMap<>();
    //用于判断@Bean和@Component是否重复注解创建相同对象
    private List<String> list_register=new ArrayList<>();


    public static BeansPool getInstance() {
        if (beansPool == null)
            beansPool = new BeansPool();

        return beansPool;
    }

    public BeansPool() {
    }



    public static BeansPool getBeansPool() {
        return beansPool;
    }



    public Map<Class, Object> getMap() {
        return map;
    }



    public List<String> getList_register() {
        return list_register;
    }

    public void setList_register(String className) {
      if(list_register==null)list_register=new ArrayList<>();
      list_register.add(className);
    }

    public Object getObject(Class clazz) {

        return map.get(clazz);
    }

    public void setObject(Class clazz, Object object) {
        map.put(clazz, object);
    }
}

