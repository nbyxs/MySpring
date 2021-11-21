package com.myspring.daoImp;

import com.myspring.dao.ISubject;

public class ISubjectImpl implements ISubject {
    public ISubjectImpl() {
    }

    @Override
    public void execute() {
        System.out.println("ISubjectImpl execute");
    }
}
