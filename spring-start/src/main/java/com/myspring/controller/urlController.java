package com.myspring.controller;


import com.myspring.bean.User;
import com.myspring.ioc.annotations.Component;
import com.myspring.annotations.RequestMapping;

@Component
public class urlController {
    @RequestMapping("/")
    public String hello(){
        return "hello springMVC!";
    }
    @RequestMapping("/hello")
    public String hello1(){
        return "hello my own springMVC!";
    }
    @RequestMapping("/use")
    public String getUser(){
        return new User(1,"name").toString();
    }

}
