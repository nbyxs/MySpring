package com.example.myspring.aop.dao.daoImp;

import com.example.myspring.aop.ISubject;

public class ISubjectImpl implements ISubject {
    public ISubjectImpl() {
    }

    @Override
    public void execute() {
        System.out.println("ISubjectImpl execute");
    }
}
