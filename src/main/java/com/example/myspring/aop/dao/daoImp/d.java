package com.example.myspring.aop.dao.daoImp;

import com.example.myspring.aop.ISubject;

public class d implements ISubject {
    @Override
    public void execute() {
        System.out.println("dsd");
    }
}
