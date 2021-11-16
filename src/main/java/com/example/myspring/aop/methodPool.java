package com.example.myspring.aop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class methodPool {
  private  static   List<methodList> methodLists;

    public methodPool() {

    }
    public static void setMethodLists(List<methodList> m){
        if(methodLists==null){
            methodLists=new ArrayList<>();
        }
        methodLists=m;
    }
    public static List<methodList> getMethodLists(){
        if(methodLists==null){
            methodLists=new ArrayList<>();
        }
        return methodLists;
    }

}
