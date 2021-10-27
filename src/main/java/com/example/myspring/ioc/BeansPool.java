package com.example.myspring.ioc;

import java.util.HashMap;
import java.util.Map;

public class BeansPool {

    private static BeansPool beansPool;

    public static BeansPool getInstance() {
        if (beansPool == null)
            beansPool = new BeansPool();
        return beansPool;
    }

    // 根据类型保存对象
    private Map<Class, Object> map = new HashMap<>();

    public Map<Class, Object> getMap() {
        return map;
    }

    public static BeansPool getBeansPool() {
        return beansPool;
    }

    public static void setBeansPool(BeansPool beansPool) {
        BeansPool.beansPool = beansPool;
    }

    public void setMap(Map<Class, Object> map) {
        this.map = map;
    }

    public Object getObject(Class clazz) {
        return map.get(clazz);
    }

    public void setObject(Class clazz, Object object) {
        map.put(clazz, object);
    }
}

