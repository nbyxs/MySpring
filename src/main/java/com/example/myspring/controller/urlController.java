package com.example.myspring.controller;


import com.example.myspring.bean.User;
import com.example.myspring.ioc.annotations.Component;
import com.example.myspring.ioc.annotations.Resource;
import com.example.myspring.mvc.annotations.RequestMapping;

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
