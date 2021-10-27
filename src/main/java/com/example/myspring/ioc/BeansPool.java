package com.example.myspring.ioc;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BeansPool {

    private static BeansPool beansPool;
    // 根据类型保存对象
    private Map<Class, Object> map = new HashMap<>();
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

    public void setList_register(String name) {
      if(list_register==null)list_register=new ArrayList<>();
      list_register.add(name);
    }

    public Object getObject(Class clazz) {

        return map.get(clazz);
    }

    public void setObject(Class clazz, Object object) {
        map.put(clazz, object);
    }
}

