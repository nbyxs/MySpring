package com.myspring.aop;

import java.util.ArrayList;
import java.util.List;
/**
 *  
 * 
 * @author nbyxs
 * @date 2021/11/17 11:37
 * @param 
 * @return
 * 存储methodList 集合
 */
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
