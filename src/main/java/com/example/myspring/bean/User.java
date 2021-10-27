package com.example.myspring.bean;

import com.example.myspring.ioc.annotations.Bean;
import com.example.myspring.ioc.annotations.Configuration;

@Configuration
public class User {

    Integer id;
    String name;

    public User(int id, String name) {
        this.id=id;
        this.name=name;
    }

    public User() {
    }

    @Bean
    public  User init(){

       return new User(1,"111");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
